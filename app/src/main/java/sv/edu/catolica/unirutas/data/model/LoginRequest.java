package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("correo")
    private String correo;

    @SerializedName("contrasena")
    private String contrasena;

    public LoginRequest(String email, String password) {
        this.correo = email;
        this.contrasena = password;
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}