package sv.edu.catolica.unirutas.data.repository;

import android.webkit.MimeTypeMap;

import sv.edu.catolica.unirutas.data.remote.Storage.SupabaseStorageApi;

import sv.edu.catolica.unirutas.data.remote.Storage.SupabaseStorageClient;
import  sv.edu.catolica.unirutas.data.repository.RutaRepository.RepositoryCallback;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.io.File;
import java.util.List;

public class StorageRepository {
    private SupabaseStorageApi api;

    public StorageRepository(){
        api = SupabaseStorageClient.getInstance();

    }

    public void uploadImage(String filePath, final RepositoryCallback<String> callback) {
        File file = new File(filePath);
        // Detectar tipo MIME automáticamente según la extensión
        String mimeType = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }

        // Si no se detecta, usar un valor por defecto (mejor que octet-stream)
        if (mimeType == null) {
            mimeType = "image/*";
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), file);

        api.uploadImage(file.getName(), fileBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String imageUrl = "https://iayjkltthotvnmugxhsq.supabase.co/storage/v1/object/public/imagenes/" + file.getName();
                    callback.onSuccess(imageUrl);
                } else {
                    callback.onError("Error al subir imagen: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    /*
    public void getPublicImage(String filename, final RepositoryCallback<String> callback) {
        String imageUrl = "https://iayjkltthotvnmugxhsq.supabase.co/storage/v1/object/public/imagenes/" + filename;
        callback.onSuccess(imageUrl);
    }*/






}
