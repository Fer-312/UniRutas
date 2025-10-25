package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Estado implements Serializable {
    @SerializedName("id_estado")
    private int idEstado;

    @SerializedName("nombre")
    private String nombre;

    // Constructor
    public Estado() {}

    public Estado(int idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}