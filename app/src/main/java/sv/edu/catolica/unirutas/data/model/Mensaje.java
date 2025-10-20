package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Mensaje {
    @SerializedName("id_mensaje")
    private int idMensaje;

    @SerializedName("id_motorista")
    private Integer idMotorista;

    @SerializedName("id_estudiante")
    private Integer idEstudiante;

    @SerializedName("id_ruta")
    private Integer idRuta;

    @SerializedName("contenido")
    private String contenido;

    @SerializedName("fecha_envio")
    private String  fechaEnvio;

    @SerializedName("leido")
    private Boolean leido;

    // Constructor
    public Mensaje() {}

    // Getters y Setters
    public int getIdMensaje() { return idMensaje; }
    public void setIdMensaje(int idMensaje) { this.idMensaje = idMensaje; }

    public Integer getIdMotorista() { return idMotorista; }
    public void setIdMotorista(Integer idMotorista) { this.idMotorista = idMotorista; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFechaEnvio() {
        try {
            // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
            // 2025-10-18 05:28:11
            if (fechaEnvio.length() >= 19) {
                String fechaFormateada = fechaEnvio.substring(0, 19)
                        .replace("T", " ")  // Reemplazar T por espacio
                        .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


                return fechaFormateada;

            }
            return fechaEnvio;
        } catch (Exception e) {
            return fechaEnvio;
        } }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}