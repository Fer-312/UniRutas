package sv.edu.catolica.unirutas.data.remote;

import sv.edu.catolica.unirutas.data.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SupabaseApi {

    // ========== LOGIN CON TU TABLA USUARIO ==========

    @GET("usuario")
    Call<List<Usuario>> getUsuarioByEmail(@Query("correo") String email);
    @GET("usuario")
    Call<List<Usuario>> getUsuarioByEmailFilter(@Query("correo") String email);

    // ========== USUARIOS ==========
    @GET("usuario")
    Call<List<Usuario>> getUsuarios();

    @GET("usuario")
    Call<List<Usuario>> getUsuarioById(@Query("id_usuario") int idUsuario);

    @POST("usuario")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @PATCH("usuario")
    Call<Usuario> updateUsuario(@Query("id_usuario") int idUsuario, @Body Usuario usuario);

    @DELETE("usuario")
    Call<Void> deleteUsuario(@Query("id_usuario") int idUsuario);

    // ========== RUTAS ==========
    @GET("ruta")
    Call<List<Ruta>> getRutas();

    @GET("ruta")
    Call<List<Ruta>> getRutaById(@Query("id_ruta") int idRuta);
    //Si queremos usar el estado de la ruta en la consulta se usara este xd
    @GET("ruta")
    Call<List<Ruta>> getRutasConEstado(@Query("select") String select);

    @GET("ruta")
    Call<List<Ruta>> getRutasConEstadoyMotorista(
            @Query("select") String select,
            @Query("id_hora_llegada") String idHora
    );







    @POST("ruta")
    Call<Ruta> createRuta(@Body Ruta ruta);

    @PATCH("ruta")
    Call<Ruta> updateRuta(@Query("id_ruta") int idRuta, @Body Ruta ruta);

    @DELETE("ruta")
    Call<Void> deleteRuta(@Query("id_ruta") int idRuta);

    // ========== ESTUDIANTES ==========
    @GET("estudiante")
    Call<List<Estudiante>> getEstudiantes();

    @GET("estudiante")
    Call<List<Estudiante>> getEstudianteById(@Query("id_estudiante") String idEstudiante);

    @GET("estudiante")
    Call<List<Estudiante>> getEstudianteByIdUsuario(@Query("id_usuario") String idUsuario);

    @POST("estudiante")
    Call<Estudiante> createEstudiante(@Body Estudiante estudiante);

    // ========== MOTORISTAS ==========
    @GET("motorista")
    Call<List<Motorista>> getMotoristas();

    @GET("motorista")
    Call<List<Motorista>> getMotoristaById(@Query("id_motorista") int idMotorista);

    // ========== INSCRIPCIONES ==========
    @GET("inscripcion")
    Call<List<Inscripcion>> getInscripciones();

    @GET("inscripcion")
    Call<List<Inscripcion>> getInscripcionesByEstudiante(
            @Query("select") String select,
            @Query("id_estudiante") String idEstudiante
    );

    @GET("inscripcion")
    Call<List<Inscripcion>> getInscripcionesByRuta(@Query("id_ruta") int idRuta);

    @GET("inscripcion")
    Call<List<Inscripcion>> getInscripcionesConRuta(@Query("select") String select);

    @POST("inscripcion")
    Call<Inscripcion> createInscripcion(@Body Inscripcion inscripcion);

    @PATCH("inscripcion")
    Call<Inscripcion> updateInscripcion(@Query("id_inscripcion") int idInscripcion, @Body Inscripcion inscripcion);

    // ========== PUNTOS DE RUTA ==========
    @GET("punto_ruta")
    Call<List<PuntoRuta>> getPuntosRuta();

    @GET("punto_ruta")
    Call<List<PuntoRuta>> getPuntosRutaDeMotoristas(
            @Query("select") String select,
            @Query("usuario.tipo_usuario.nombre") String filtro
    );


    @GET("punto_ruta")
    Call<List<PuntoRuta>> getPuntosByRuta(
            @Query("select") String select,
            @Query("id_ruta") String idRuta
        );

    @POST("punto_ruta")
    Call<PuntoRuta> createPuntoRuta(@Body PuntoRuta puntoRuta);

    // ========== COMENTARIOS ==========
    @GET("comentario")
    Call<List<Comentario>> getComentarios();

    @GET("comentario")
    Call<List<Comentario>> getComentariosByRuta(@Query("id_ruta") int idRuta);

    //@GET("comentario")
    //Call<List<Comentario>> getComentariosByMotorista(@Query("id_ruta") String select);

    @GET("comentario")
    Call<List<Comentario>> getComentariosPorMotorista(
            @Query("select") String select,
            @Query("ruta.id_motorista") String idMotorista
    );



    @POST("comentario")
    Call<Comentario> createComentario(@Body Comentario comentario);

    // ========== MENSAJES ==========
    @GET("mensaje")
    Call<List<Mensaje>> getMensajes();

    @GET("mensaje")
    Call<List<Mensaje>> getMensajesByRuta(@Query("id_ruta") int idRuta);

    @POST("mensaje")
    Call<Mensaje> createMensaje(@Body Mensaje mensaje);

    // ========== NOTIFICACIONES ==========
    @GET("notificacion")
    Call<List<Notificacion>> getNotificaciones();

    @GET("notificacion")
    Call<List<Notificacion>> getNotificacionesByRuta(@Query("id_ruta") int idRuta);

    @POST("notificacion")
    Call<Notificacion> createNotificacion(@Body Notificacion notificacion);
    // ========== HORAIOS ==========
    @GET("horario")
    Call<List<Horario>> getHorarios();



    // ========== FAVORITOS ==========
    @GET("favorito")
    Call<List<Favorito>> getFavoritos(@Query("select") String select);

    @GET("favorito")
    Call<List<Favorito>> getFavoritosByUsuario(
            @Query("id_usuario") String idUsuario,
            @Query("select") String select
    );

    @POST("favorito")
    Call<Favorito> createFavorito(@Body Favorito favorito);

    @DELETE("favorito")
    Call<Void> deleteFavorito(@Query("id_favorito") int idFavorito);

    // ========== MICROBUSES ==========
    @GET("microbus")
    Call<List<Microbus>> getMicrobuses();

    @GET("microbus")
    Call<List<Microbus>> getMicrobusByOrganizacion(@Query("id_organizacion") int idOrganizacion);

    // ========== ORGANIZACIONES ==========
    @GET("organizacion")
    Call<List<Organizacion>> getOrganizaciones();
}