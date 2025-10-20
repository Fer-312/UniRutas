package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Comentario {
    @SerializedName("id_comentario")
    private int idComentario;

    @SerializedName("id_estudiante")
    private Integer idEstudiante;

    @SerializedName("id_ruta")
    private Integer idRuta;

    @SerializedName("contenido")
    private String contenido;

    @SerializedName("puntuacion")
    private BigDecimal puntuacion;

    @SerializedName("fecha_comentario")
    private String fechaComentario;

    // Constructor
    public Comentario() {}

    // Getters y Setters
    public int getIdComentario() { return idComentario; }
    public void setIdComentario(int idComentario) { this.idComentario = idComentario; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public BigDecimal getPuntuacion() { return puntuacion; }
    public void setPuntuacion(BigDecimal puntuacion) { this.puntuacion = puntuacion; }

    public String getFechaComentario() { return fechaComentario; }
    public void setFechaComentario(String fechaComentario) { this.fechaComentario = fechaComentario; }
}