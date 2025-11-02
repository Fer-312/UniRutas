package sv.edu.catolica.unirutas.data.remote.Storage;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.catolica.unirutas.data.remote.SupabaseApi;
import sv.edu.catolica.unirutas.utils.Constants;

public class SupabaseStorageClient {

    private static SupabaseStorageApi instance;
    private static String authToken;

    public static SupabaseStorageApi getInstance() {
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
                                .header("Content-Type", "application/octet-stream")
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
                    .baseUrl(Constants.BASE_URL_STORAGE)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(SupabaseStorageApi.class);
        }
        return instance;
    }
}
