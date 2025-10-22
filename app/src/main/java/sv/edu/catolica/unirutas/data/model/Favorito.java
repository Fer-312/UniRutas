package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class Favorito {
    @SerializedName("id_favorito")
    private int idFavorito;

    @SerializedName("id_usuario")
    private Integer idUsuario;


    @SerializedName("fecha_agregado")
    private String fechaAgregado;

    @SerializedName("id_hora")
    private String idHora;
    private Horario horario;

    // Constructor
    public Favorito() {}

    // Getters y Setters
    public int getIdFavorito() { return idFavorito; }
    public void setIdFavorito(int idFavorito) { this.idFavorito = idFavorito; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }



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

    public String getIdHora() {
        return idHora;
    }

    public void setIdHorario(String idHora) {
        this.idHora = idHora;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHora(Horario horario) {
        this.horario = horario;
    }
}