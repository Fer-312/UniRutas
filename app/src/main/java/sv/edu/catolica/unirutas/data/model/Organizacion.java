package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class Organizacion {
    @SerializedName("id_organizacion")
    private int idOrganizacion;

    @SerializedName("id_usuario")
    private Integer idUsuario;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("estado_org")
    private String estadoOrg;

    // Constructor
    public Organizacion() {}

    // Getters y Setters
    public int getIdOrganizacion() { return idOrganizacion; }
    public void setIdOrganizacion(int idOrganizacion) { this.idOrganizacion = idOrganizacion; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEstadoOrg() { return estadoOrg; }
    public void setEstadoOrg(String estadoOrg) { this.estadoOrg = estadoOrg; }
}