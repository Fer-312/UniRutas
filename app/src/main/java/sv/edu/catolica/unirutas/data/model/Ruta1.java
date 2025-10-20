package sv.edu.catolica.unirutas.data.model;

public class Ruta1 {
    private String origen;
    private String destino;
    private String horario;
    private String estado;

    public Ruta1(String origen, String destino, String horario, String estado) {
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.estado = estado;
    }

    // Getters y setters
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getHorario() { return horario; }
    public String getEstado() { return estado; }
}
