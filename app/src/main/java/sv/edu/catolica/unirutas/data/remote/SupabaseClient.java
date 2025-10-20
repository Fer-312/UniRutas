package sv.edu.catolica.unirutas.data.remote;

import sv.edu.catolica.unirutas.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class SupabaseClient {
    private static SupabaseApi instance;
    private static String authToken;
    public static SupabaseApi getInstance() {
        if (instance == null) {

            // Interceptor para logging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Cliente HTTP con headers
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        // DEBUG: Imprimir la URL y headers para verificar
                        System.out.println("URL: " + original.url());
                        System.out.println("API Key: " + Constants.SUPABASE_ANON_KEY);

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("apikey", Constants.SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + Constants.SUPABASE_ANON_KEY)
                                .header("Content-Type", "application/json")
                                .header("Prefer", "return=representation");

                        // IMPORTANTE: Usar authToken si existe, sino usar API Key
                        if (authToken != null && !authToken.isEmpty()) {
                            requestBuilder.header("Authorization", "Bearer " + authToken);
                            System.out.println("Using AUTH Token: " + authToken.substring(0, 20) + "...");
                        } else {
                            requestBuilder.header("Authorization", "Bearer " + Constants.SUPABASE_ANON_KEY);
                            System.out.println("Using ANON Token");
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(SupabaseApi.class);
        }
        return instance;
    }


    // ========== MÉTODOS NUEVOS PARA AUTH ==========

    /**
     * Establece el token de autenticación para requests autenticadas
     */
    public static void setAuthToken(String token) {
        authToken = token;
        // Forzar recreación del cliente con el nuevo token
        instance = null;
        System.out.println("Auth Token SET: " + (token != null ? token.substring(0, 20) + "..." : "null"));
    }

    /**
     * Limpia el token de autenticación (logout)
     */
    public static void clearAuthToken() {
        authToken = null;
        // Forzar recreación del cliente sin token
        instance = null;
        System.out.println("Auth Token CLEARED");
    }

    /**
     * Verifica si hay un token de autenticación activo
     */
    public static boolean hasAuthToken() {
        return authToken != null && !authToken.isEmpty();
    }

    /**
     * Obtiene el token actual (para debugging)
     */
    public static String getAuthToken() {
        return authToken;
    }

}