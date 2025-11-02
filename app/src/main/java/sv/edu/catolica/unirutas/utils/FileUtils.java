package sv.edu.catolica.unirutas.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import sv.edu.catolica.unirutas.ui.main.MainActivity;

public class FileUtils {

    public static String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }
    public static void MostrarImagen(Context context,ImageView imageView, String imageUrl){
        if(imageUrl!=null){
            Glide.with(context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(imageView);
        }
    }
    public static void MostrarImagenMicrobus(Context context,ImageView imageView, String imageUrl){
        if(imageUrl!=null){
            Glide.with(context)
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}
