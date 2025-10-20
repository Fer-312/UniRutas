package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;

public class Hora {
    @SerializedName("id_hora")
    private int idHora;
    @SerializedName("hora")
    private String hora;

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public LocalTime getHora() {
        return LocalTime.parse(hora);
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
