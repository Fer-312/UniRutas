package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Microbus implements Serializable {
    @SerializedName("id_microbus")
    private int idMicrobus;

    @SerializedName("placa_vehiculo")
    private String placaVehiculo;

    @SerializedName("estado_microbus")
    private String estadoMicrobus;

    @SerializedName("id_organizacion")
    private Integer idOrganizacion;

    @SerializedName("foto_microbus")
    private String fotoMicrobus;

    // Constructor
    public Microbus() {}

    // Getters y Setters
    public int getIdMicrobus() { return idMicrobus; }
    public void setIdMicrobus(int idMicrobus) { this.idMicrobus = idMicrobus; }

    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }

    public String getEstadoMicrobus() { return estadoMicrobus; }
    public void setEstadoMicrobus(String estadoMicrobus) { this.estadoMicrobus = estadoMicrobus; }

    public Integer getIdOrganizacion() { return idOrganizacion; }
    public void setIdOrganizacion(Integer idOrganizacion) { this.idOrganizacion = idOrganizacion; }

    public String getFotoMicrobus() { return fotoMicrobus; }
    public void setFotoMicrobus(String fotoMicrobus) { this.fotoMicrobus = fotoMicrobus; }
}