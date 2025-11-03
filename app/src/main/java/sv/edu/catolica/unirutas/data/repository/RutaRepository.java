package sv.edu.catolica.unirutas.data.repository;

import android.util.Log;

import com.google.gson.Gson;

import sv.edu.catolica.unirutas.data.remote.SupabaseApi;
import sv.edu.catolica.unirutas.data.remote.SupabaseClient;
import sv.edu.catolica.unirutas.data.remote.ApiResponse;
import sv.edu.catolica.unirutas.data.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class RutaRepository {
    private SupabaseApi api;


    public RutaRepository() {
        api = SupabaseClient.getInstance();
    }

    // Interface para callbacks
    public interface RepositoryCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }

    // ========== RUTAS ==========
    public void getRutas(final RepositoryCallback<List<Ruta>> callback) {
        api.getRutas().enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener rutas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
    //la ruta con el select
    public void getRutasConEstado(final RepositoryCallback<List<Ruta>> callback) {
        api.getRutasConEstado("*,estado(*),motorista(*,usuario(*)),microbus(*)").enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener rutas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });

    }

    public void getRutasConEstadoyMotorista(int idHora,final RepositoryCallback<List<Ruta>> callback) {
        api.getRutasConEstadoyMotorista("*,estado(*),motorista(*,usuario(*)),microbus(*)","eq."+idHora).enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener rutas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                callback.onError("por xdError de conexión: " + t.getMessage());
            }
        });

    }

    public void getRutaById(int idRuta, final RepositoryCallback<List<Ruta>> callback) {
        api.getRutaById(idRuta).enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener ruta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void createRuta(Ruta ruta, final RepositoryCallback<Ruta> callback) {
        api.createRuta(ruta).enqueue(new Callback<Ruta>() {
            @Override
            public void onResponse(Call<Ruta> call, Response<Ruta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear ruta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Ruta> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== ESTUDIANTES ==========

    public void getEstudianteByIdUsuario (int idUsuario,final RepositoryCallback<List<Estudiante>> callback) {
        api.getEstudianteByIdUsuario("eq." +idUsuario).enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener estudiantes: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }


    public void getEstudiantes(final RepositoryCallback<List<Estudiante>> callback) {
        api.getEstudiantes().enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener estudiantes: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
    // ========== MOTORISTA ==========
    public void getMotoristaByIdUsuario (int idUsuario,final RepositoryCallback<List<Motorista>> callback) {
        api.getMotoristaByIdUsuario("eq." +idUsuario).enqueue(new Callback<List<Motorista>>() {
            @Override
            public void onResponse(Call<List<Motorista>> call, Response<List<Motorista>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener inscripciones: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Motorista>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== INSCRIPCIONES ==========
    public void getInscripcionesByEstudiante(int idEstudiante, final RepositoryCallback<List<Inscripcion>> callback) {
        api.getInscripcionesByEstudiante("count", "eq." + idEstudiante).enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener inscripciones: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getInscripcionesByRuta(int idRuta, final RepositoryCallback<List<Inscripcion>> callback) {
        api.getInscripcionesByRuta("id_estudiante", "eq." + idRuta).enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener inscripciones: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getInscripcionesConRuta(final RepositoryCallback<List<Inscripcion>> callback) {
        api.getInscripcionesConRuta("*,ruta(*,estado(*),motorista(*,usuario(*)),microbus(*))").enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }else {
                    callback.onError("Error al obtener inscripciones: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }


    public void createInscripcion(Inscripcion inscripcion, final RepositoryCallback<Inscripcion> callback) {
        api.createInscripcion(inscripcion).enqueue(new Callback<Inscripcion>() {
            @Override
            public void onResponse(Call<Inscripcion> call, Response<Inscripcion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al crear inscripción: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inscripcion> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== PUNTOS DE RUTA ==========
    public void getPuntosRuta(final RepositoryCallback<List<PuntoRuta>> callback) {
        api.getPuntosRuta().enqueue(new Callback<List<PuntoRuta>>() {
            @Override
            public void onResponse(Call<List<PuntoRuta>> call, Response<List<PuntoRuta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener puntos de ruta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PuntoRuta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getPuntosRutaDeMotoristas(final RepositoryCallback<List<PuntoRuta>> callback) {
        api.getPuntosRutaDeMotoristas("*,usuario!inner(tipo_usuario!inner())","eq.Motorista").enqueue(new Callback<List<PuntoRuta>>() {
            @Override
            public void onResponse(Call<List<PuntoRuta>> call, Response<List<PuntoRuta>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // 🔹 LOGS DE DEPURACIÓN 🔹
                    Log.d("API_DEBUG", "Código HTTP: " + response.code());

                    if (response.body() != null) {
                        Log.d("API_DEBUG", "Respuesta JSON: " + new Gson().toJson(response.body()));
                    } else {
                        Log.d("API_DEBUG", "Respuesta vacía o nula");
                    }

                    callback.onSuccess(response.body());
                } else {

                    callback.onError("Error al obtener puntos de ruta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PuntoRuta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getPuntosByRuta(int idRuta, final RepositoryCallback<List<PuntoRuta>> callback) {
        api.getPuntosByRuta("*", "eq." + idRuta).enqueue(new Callback<List<PuntoRuta>>() {
            @Override
            public void onResponse(Call<List<PuntoRuta>> call, Response<List<PuntoRuta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener puntos de ruta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PuntoRuta>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== COMENTARIOS ==========
    public void getComentariosByRuta(int idRuta, final RepositoryCallback<List<Comentario>> callback) {
        api.getComentariosByRuta(idRuta).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener comentarios: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getComentariosPorMotorista(int idMotorista, final RepositoryCallback<List<Comentario>> callback) {
        api.getComentariosPorMotorista("*,estudiante(id_usuario,usuario(nombre,apellido,foto_perfil)),ruta!inner(id_motorista))", "eq." + idMotorista).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener comentarios: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void createComentario(Comentario comentario, final RepositoryCallback<List<Comentario>> callback) {
        api.createComentario(comentario).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener horarios: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== HORARIO ===========
    public void getHorarios(final RepositoryCallback<List<Horario>> callback) {
        api.getHorarios().enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener horarios: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== FAVORITOS ==========
    public void getFavoritos(final RepositoryCallback<List<Favorito>> callback) {
        api.getFavoritos("*,hora(*)").enqueue(new Callback<List<Favorito>>() {
            @Override
            public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener favoritos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Favorito>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void getFavoritosByUsuario(int idUsuario, final RepositoryCallback<List<Favorito>> callback) {
        api.getFavoritosByUsuario("eq." +idUsuario, "*,horario(*)").enqueue(new Callback<List<Favorito>>() {
            @Override
            public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener favoritos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Favorito>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void createFavorito(Favorito favorito, final RepositoryCallback<Favorito> callback) {
        api.createFavorito(favorito).enqueue(new Callback<Favorito>() {
            @Override
            public void onResponse(Call<Favorito> call, Response<Favorito> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al agregar favorito: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Favorito> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void deleteFavorito(int idFavorito, final RepositoryCallback<Void> callback) {
        api.deleteFavorito(idFavorito).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Error al eliminar favorito: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ========== USUARIOS ==========
    public void getUsuarioById(int idUsuario, final RepositoryCallback<List<Usuario>> callback) {
        api.getUsuarioById(idUsuario).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
    public void updateUsuario(int idUsuario, Usuario usuario, final RepositoryCallback<List<Usuario>> callback) {
        api.updateUsuario("eq."+idUsuario, usuario).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }


    public void createUsuario(Usuario usuario, final RepositoryCallback<List<Usuario>> callback) {
        api.createUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
}