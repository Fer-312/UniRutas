package sv.edu.catolica.unirutas.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import sv.edu.catolica.unirutas.data.model.Estudiante;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.remote.SupabaseApi;
import sv.edu.catolica.unirutas.data.remote.SupabaseClient;
import sv.edu.catolica.unirutas.data.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.edu.catolica.unirutas.ui.main.auth.LoginActivity;

import java.util.List;

public class AuthRepository {
    private SupabaseApi api;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "UsuarioPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ESTUDENT_ID = "estudent_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_FOTO = "foto_perfil";
    private static final String KEY_MOTORISTA_ID = "motorista_id";

    public int estudiantexdId=0;

    public AuthRepository(Context context) {
        this.api = SupabaseClient.getInstance();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public interface AuthCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }



    public void login(String email, String password, AuthCallback callback) {

        // Usar consulta con filtro correcto
        String emailFilter = "eq." + email;



        api.getUsuarioByEmailFilter(emailFilter).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Usuario> usuarios = response.body();


                    if (!usuarios.isEmpty()) {
                        Usuario usuario = usuarios.get(0);


                        // Verificar contraseña localmente

                        if (usuario.getContrasena() != null && usuario.getContrasena().equals(password)) {
                            saveUserData(usuario);
                            callback.onSuccess(usuario);
                        } else {

                            callback.onError("Contraseña incorrecta");
                        }
                    } else {
                        callback.onError("No existe usuario con ese email");
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onError("Error al buscar usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

                callback.onError("Error de conexión ");
            }
        });


    }

    // REGISTRO en tu tabla usuario
    public void register(Usuario usuario, AuthCallback callback) {
        api.createUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario nuevoUsuario = response.body();
                    saveUserData(nuevoUsuario);
                    callback.onSuccess(nuevoUsuario);
                } else {
                    callback.onError("Error en registro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // OBTENER usuario por email
    public void getUsuarioByEmail(String email, AuthCallback callback) {
        api.getUsuarioByEmail(email).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body().get(0));
                } else {
                    callback.onError("Usuario no encontrado");
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // CERRAR SESIÓN - LIMPIAR
    public void logout() {
        prefs.edit()
                .remove(KEY_USER_ID)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_NAME)
                .remove(KEY_ESTUDENT_ID)
                .remove(KEY_USER_PHONE)
                .remove(KEY_USER_FOTO)
                .remove(KEY_MOTORISTA_ID)
                .apply();
    }


    // VERIFICAR SI ESTÁ LOGUEADO
    public boolean isLoggedIn() {
        return prefs.getString(KEY_USER_EMAIL, null) != null;
    }

    // OBTENER USUARIO ACTUAL
    public Usuario getCurrentUser() {
        if (!isLoggedIn()) return null;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(prefs.getInt(KEY_USER_ID, 0));
        usuario.setCorreo(prefs.getString(KEY_USER_EMAIL, ""));
        usuario.setNombre(prefs.getString(KEY_USER_NAME, ""));
        usuario.setTelefono(prefs.getString(KEY_USER_PHONE, ""));
        usuario.setFotoPerfil(prefs.getString(KEY_USER_FOTO, ""));

        return usuario;
    }
    public int getCurrentEstudianteID() {
        if (!isLoggedIn()) return 0;
        estudiantexdId = prefs.getInt(KEY_ESTUDENT_ID, 0);
        return prefs.getInt(KEY_ESTUDENT_ID, 0);
    }

    // GUARDAR DATOS DEL USUARIO
    public void saveUserData( int estudianteId) {
        estudiantexdId=estudianteId;
        prefs.edit()
                .putInt(KEY_ESTUDENT_ID, estudianteId)
                .apply();
    }
    // GUARDAR DATOS DEL USUARIO
    public void saveUserDataMotorista( int motoristaId) {
        estudiantexdId=motoristaId;
        prefs.edit()
                .putInt(KEY_MOTORISTA_ID, motoristaId)
                .apply();
    }
    public void saveUserData(Usuario usuario) {
        prefs.edit()
                .putInt(KEY_USER_ID, usuario.getIdUsuario())
                .putString(KEY_USER_PHONE, usuario.getTelefono())
                .putString(KEY_USER_EMAIL, usuario.getCorreo())
                .putString(KEY_USER_NAME, usuario.getNombre())
                .putString(KEY_USER_FOTO, usuario.getFotoPerfil())
                .apply();
    }
}