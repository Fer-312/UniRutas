package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Notificacion {
    @SerializedName("id_notificacion")
    private int idNotificacion;

    @SerializedName("id_ruta")
    private Integer idRuta;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("fecha_envio")
    private String fechaEnvio;

    @SerializedName("leido")
    private Boolean leido;

    // Constructor
    public Notificacion() {}

    // Getters y Setters
    public int getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(int idNotificacion) { this.idNotificacion = idNotificacion; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}