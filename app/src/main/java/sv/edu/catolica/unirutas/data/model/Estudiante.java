package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class Estudiante {
    @SerializedName("id_estudiante")
    private int idEstudiante;

    @SerializedName("id_usuario")
    private int idUsuario;
    private Usuario usuario;


    @SerializedName("carnet")
    private String carnet;

    @SerializedName("universidad")
    private String universidad;

    // Constructor
    public Estudiante() {}

    // Getters y Setters
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getCarnet() { return carnet; }
    public void setCarnet(String carnet) { this.carnet = carnet; }

    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}