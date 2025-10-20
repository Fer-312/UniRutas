package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class TipoUsuario {
    @SerializedName("id_tipo")
    private int idTipo;

    @SerializedName("nombre")
    private String nombre;

    // Constructor
    public TipoUsuario() {}

    public TipoUsuario(int idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}