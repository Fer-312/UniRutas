package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class PuntoRuta implements Serializable {
    @SerializedName("id_punto")
    private int idPunto;

    @SerializedName("id_ruta")
    private Integer idRuta;
    private Ruta ruta;

    @SerializedName("id_usuario")
    private Integer idUsuario;
    private Usuario usuario;

    @SerializedName("tipo_ruta")
    private Boolean tipoRuta;

    @SerializedName("latitud")
    private BigDecimal latitud;

    @SerializedName("longitud")
    private BigDecimal longitud;

    @SerializedName("orden")
    private Integer orden;

    @SerializedName("nombre")
    private String Nombre;


    // Getters y Setters
    public int getIdPunto() { return idPunto; }
    public void setIdPunto(int idPunto) { this.idPunto = idPunto; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Boolean getTipoRuta() { return tipoRuta; }
    public void setTipoRuta(Boolean tipoRuta) { this.tipoRuta = tipoRuta; }

    public BigDecimal getLatitud() { return latitud; }
    public void setLatitud(BigDecimal latitud) { this.latitud = latitud; }

    public BigDecimal getLongitud() { return longitud; }
    public void setLongitud(BigDecimal longitud) { this.longitud = longitud; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}