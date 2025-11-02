package sv.edu.catolica.unirutas.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Usuario;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.data.repository.StorageRepository;
import sv.edu.catolica.unirutas.utils.FileUtils;

public class detail_editar_perfil extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageUri;
    private ImageView imageView;
    private Button btnSelect, btnUpload;
    private  ImageView btnregresar;
    private EditText etNombre,etApellido,etTelefono;
    private TextView tvEncabezado;
    private AuthRepository auth;
    private RutaRepository repository;
    private String imgUrl;

    private StorageRepository storageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_editar_perfil);

        initComponentes();


        // ☁️ Subir imagen a Supabase
    }
    private void initComponentes(){
        imgUrl="";
        storageRepository = new StorageRepository();
        repository = new RutaRepository();
        auth = new AuthRepository(detail_editar_perfil.this);
        btnregresar = findViewById(R.id.btnregresar);
        etTelefono = findViewById(R.id.etTelefono);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        tvEncabezado = findViewById(R.id.tvEncabezado);
        tvEncabezado.setText("Cambiar información de Perfil");
        imageView = findViewById(R.id.imageViewPreview);
        btnSelect = findViewById(R.id.btnSelectImage);
        btnUpload = findViewById(R.id.btnUploadImage);
        Usuario user = auth.getCurrentUser();
        FileUtils.MostrarImagen(detail_editar_perfil.this,imageView,user.getFotoPerfil());
        //Regresar
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Seleccion de img
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Primero se solicita el permiso para evitar el cerrado de la app
                checkPermissionAndOpenGallery();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    try {
                        String filePath = FileUtils.getPath(detail_editar_perfil.this, imageUri);
                        if (filePath != null) {
                            subirImagen(filePath);
                        } else {
                            Toast.makeText(detail_editar_perfil.this, "Error obteniendo la ruta de la imagen", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    }
                }else{
                    subirInformacion(imgUrl);
                }
            }
        });


    }

    private static final int PERMISSION_REQUEST_CODE = 200;

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        } else {
            // Android 13 (Tiramisu) usa READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permiso denegado, no se puede acceder a la galería", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                FileUtils.MostrarImagen(detail_editar_perfil.this,imageView,imageUri.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void subirInformacion(String fotoUrl){
        String tel,nombre,apellido;
        tel= etTelefono.getText().toString();
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();



        Usuario user = auth.getCurrentUser();
        if(!nombre.isEmpty()){user.setNombre(nombre);}
        if(!apellido.isEmpty()){user.setApellido(apellido);}
        if(!fotoUrl.isBlank()){user.setFotoPerfil(fotoUrl);}
        if(!tel.isEmpty()){user.setTelefono(tel);}




        repository.updateUsuario(user.getIdUsuario(), user, new RutaRepository.RepositoryCallback<List<Usuario>>() {
            @Override
            public void onSuccess(List<Usuario> data) {
                Toast.makeText(detail_editar_perfil.this, "Se ha modificado con exito", Toast.LENGTH_SHORT).show();
                if (data != null && !data.isEmpty()) {
                    auth.saveUserData(data.get(0));
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(detail_editar_perfil.this, error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void subirImagen(String filePath) {

        Toast.makeText(this, "Subiendo imagen...", Toast.LENGTH_SHORT).show();

        storageRepository.uploadImage(filePath, new RutaRepository.RepositoryCallback<String>() {
            @Override
            public void onSuccess(String imageUrl) {
                Toast.makeText(detail_editar_perfil.this, "Se ha modificado con exito", Toast.LENGTH_SHORT).show();
                FileUtils.MostrarImagen(detail_editar_perfil.this,imageView,imageUrl);
                subirInformacion(imageUrl);


            }

            @Override
            public void onError(String error) {
                Toast.makeText(detail_editar_perfil.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
