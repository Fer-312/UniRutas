package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class Favorito {
    @SerializedName("id_favorito")
    private int idFavorito;

    @SerializedName("id_usuario")
    private Integer idUsuario;

    @SerializedName("id_ruta")
    private Integer idRuta;
    private Ruta ruta;

    @SerializedName("fecha_agregado")
    private String fechaAgregado;

    @SerializedName("id_hora")
    private int idHora;
    private Hora hora;

    // Constructor
    public Favorito() {}

    // Getters y Setters
    public int getIdFavorito() { return idFavorito; }
    public void setIdFavorito(int idFavorito) { this.idFavorito = idFavorito; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getFechaAgregado() {         try {
        // Tomar solo la parte hasta los segundos (primeros 19 caracteres)
        // 2025-10-18 05:28:11
        if (fechaAgregado.length() >= 19) {
            String fechaFormateada = fechaAgregado.substring(0, 19)
                    .replace("T", " ")  // Reemplazar T por espacio
                    .replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // yyyy-mm-dd a dd/mm/yyyy


            return fechaFormateada;

        }
        return fechaAgregado;
    } catch (Exception e) {
        return fechaAgregado;
    } }
    public void setFechaAgregado(String fechaAgregado) { this.fechaAgregado = fechaAgregado; }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public Hora getHora() {
        return hora;
    }

    public void setHora(Hora hora) {
        this.hora = hora;
    }
}