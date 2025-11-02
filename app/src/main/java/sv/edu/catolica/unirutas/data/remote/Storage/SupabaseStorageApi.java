package sv.edu.catolica.unirutas.data.remote.Storage;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SupabaseStorageApi {

    // 🔹 Subir imagen al bucket
    @POST("object/imagenes/{filename}")
    Call<ResponseBody> uploadImage(
            @Path("filename") String filename,
            @Body RequestBody fileBody
    );

    // 🔹 Obtener URL pública (si el bucket está público)
    @GET("object/public/imagenes/{filename}")
    Call<ResponseBody> getPublicImage(
            @Path("filename") String filename
    );
}