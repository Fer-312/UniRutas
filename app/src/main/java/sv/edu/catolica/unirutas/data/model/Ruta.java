package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

public class Ruta implements Serializable {
    @SerializedName("id_ruta")
    private int idRuta;

    @SerializedName("id_motorista")
    private Integer idMotorista;
    private Motorista motorista;

    @SerializedName("municipio_origen")
    private String municipioOrigen;

    @SerializedName("municipio_destino")
    private String municipioDestino;

    @SerializedName("hora_salida")
    private String horaSalida;

    @SerializedName("hora_llegada")
    private String horaLlegada;

    @SerializedName("tarifa")
    private BigDecimal tarifa;

    @SerializedName("capacidad_total")
    private Integer capacidadTotal;

    @SerializedName("id_estado")
    private Integer idEstado;
    private Estado estado;

    @SerializedName("fecha_creacion")
    private String fechaCreacion;

    @SerializedName("id_microbus")
    private Integer idMicrobus;
    private Microbus microbus;

    // Constructor
    public Ruta() {}

    // Getters y Setters
    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public Integer getIdMotorista() { return idMotorista; }
    public void setIdMotorista(Integer idMotorista) { this.idMotorista = idMotorista; }

    public String getMunicipioOrigen() { return municipioOrigen; }
    public void setMunicipioOrigen(String municipioOrigen) { this.municipioOrigen = municipioOrigen; }

    public String getMunicipioDestino() { return municipioDestino; }
    public void setMunicipioDestino(String municipioDestino) { this.municipioDestino = municipioDestino; }

    public LocalTime getHoraSalida() { return LocalTime.parse(horaSalida); }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public LocalTime getHoraLlegada() { return LocalTime.parse(horaLlegada); }
    public void setHoraLlegada(String horaLlegada) { this.horaLlegada = horaLlegada; }

    public BigDecimal getTarifa() { return tarifa; }
    public void setTarifa(BigDecimal tarifa) { this.tarifa = tarifa; }

    public Integer getCapacidadTotal() { return capacidadTotal; }
    public void setCapacidadTotal(Integer capacidadTotal) { this.capacidadTotal = capacidadTotal; }



    public String getFechaCreacion() {
        try {
            // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
            // 2025-10-18 05:28:11
            if (fechaCreacion.length() >= 19) {
                String fechaFormateada = fechaCreacion.substring(0, 19)
                        .replace("T", " ")  // Reemplazar T por espacio
                        .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


                return fechaFormateada;

            }
            return fechaCreacion;
        } catch (Exception e) {
            return fechaCreacion;
        }
    }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getIdMicrobus() { return idMicrobus; }
    public void setIdMicrobus(Integer idMicrobus) { this.idMicrobus = idMicrobus; }

    public Estado getEstado() { return estado;}

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Microbus getMicrobus() {
        return microbus;
    }

    public void setMicrobus(Microbus microbus) {
        this.microbus = microbus;
    }
}