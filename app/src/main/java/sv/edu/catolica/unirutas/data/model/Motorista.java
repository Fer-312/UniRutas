package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Motorista implements Serializable {
    @SerializedName("id_motorista")
    private int idMotorista;

    @SerializedName("id_usuario")
    private Integer idUsuario;
    private Usuario usuario;

    @SerializedName("calificacion_promedio")
    private BigDecimal calificacionPromedio;

    @SerializedName("estado_cuenta")
    private Boolean estadoCuenta;

    @SerializedName("id_organizacion")
    private Integer idOrganizacion;

    // Constructor
    public Motorista() {}

    // Getters y Setters
    public int getIdMotorista() { return idMotorista; }
    public void setIdMotorista(int idMotorista) { this.idMotorista = idMotorista; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public BigDecimal getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(BigDecimal calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public Boolean getEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(Boolean estadoCuenta) { this.estadoCuenta = estadoCuenta; }

    public Integer getIdOrganizacion() { return idOrganizacion; }
    public void setIdOrganizacion(Integer idOrganizacion) { this.idOrganizacion = idOrganizacion; }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}