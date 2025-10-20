package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Inscripcion {
    @SerializedName("id_inscripcion")
    private int idInscripcion;

    @SerializedName("id_estudiante")
    private Integer idEstudiante;

    @SerializedName("id_ruta")
    private Integer idRuta;
    private Ruta ruta;

    @SerializedName("fecha_inscripcion")
    private String fechaInscripcion;

    @SerializedName("estado_inscripcion")
    private Boolean estadoInscripcion;

    @SerializedName("tarifa_pagada")
    private BigDecimal tarifaPagada;

    @SerializedName("fecha_asistencia")
    private String fechaAsistencia;

    @SerializedName("estado_asistencia")
    private Boolean estadoAsistencia;

    @SerializedName("codigo_qr")
    private String codigoQr;

    // Constructor
    public Inscripcion() {}

    // Getters y Setters
    public int getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(int idInscripcion) { this.idInscripcion = idInscripcion; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getFechaInscripcion() { try {
        // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
        // 2025-10-18 05:28:11
        if (fechaInscripcion.length() >= 19) {
            String fechaFormateada = fechaInscripcion.substring(0, 19)
                    .replace("T", " ")  // Reemplazar T por espacio
                    .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


            return fechaFormateada;

        }
        return fechaInscripcion;
    } catch (Exception e) {
        return fechaInscripcion;
    } }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public Boolean getEstadoInscripcion() { return estadoInscripcion; }
    public void setEstadoInscripcion(Boolean estadoInscripcion) { this.estadoInscripcion = estadoInscripcion; }

    public BigDecimal getTarifaPagada() { return tarifaPagada; }
    public void setTarifaPagada(BigDecimal tarifaPagada) { this.tarifaPagada = tarifaPagada; }

    public String getFechaAsistencia() { try {
        // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
        // 2025-10-18 05:28:11
        if (fechaAsistencia.length() >= 19) {
            String fechaFormateada = fechaAsistencia.substring(0, 19)
                    .replace("T", " ")  // Reemplazar T por espacio
                    .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


            return fechaFormateada;

        }
        return fechaAsistencia;
    } catch (Exception e) {
        return fechaAsistencia;
    } }
    public void setFechaAsistencia(String fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public Boolean getEstadoAsistencia() { return estadoAsistencia; }
    public void setEstadoAsistencia(Boolean estadoAsistencia) { this.estadoAsistencia = estadoAsistencia; }

    public String getCodigoQr() { return codigoQr; }
    public void setCodigoQr(String codigoQr) { this.codigoQr = codigoQr; }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}