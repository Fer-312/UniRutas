package sv.edu.catolica.unirutas.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("user_metadata")
    private UserMetadata userMetadata;

    public RegisterRequest(String email, String password, String nombre, String apellido) {
        this.email = email;
        this.password = password;
        this.userMetadata = new UserMetadata(nombre, apellido);
    }

    public static class UserMetadata {
        @SerializedName("nombre")
        private String nombre;

        @SerializedName("apellido")
        private String apellido;

        public UserMetadata(String nombre, String apellido) {
            this.nombre = nombre;
            this.apellido = apellido;
        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserMetadata getUserMetadata() { return userMetadata; }
    public void setUserMetadata(UserMetadata userMetadata) { this.userMetadata = userMetadata; }
}