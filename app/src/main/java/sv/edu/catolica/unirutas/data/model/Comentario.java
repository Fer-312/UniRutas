package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Comentario {
    @SerializedName("id_comentario")
    private int idComentario;

    @SerializedName("id_estudiante")
    private Integer idEstudiante;
    private Estudiante estudiante;

    @SerializedName("id_ruta")
    private Integer idRuta;
    private Ruta ruta;

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

    public String getFechaComentario() {
        try {
            // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
            // 2025-10-18 05:28:11
            if (fechaComentario.length() >= 19) {
                String fechaFormateada = fechaComentario.substring(0, 19)
                        .replace("T", " ")  // Reemplazar T por espacio
                        .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


                return fechaFormateada;

            }
            return fechaComentario;
        } catch (Exception e) {
            return fechaComentario;
        } }
    public void setFechaComentario(String fechaComentario) { this.fechaComentario = fechaComentario; }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}