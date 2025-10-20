package sv.edu.catolica.unirutas.ui.main.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Usuario;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        authRepository = new AuthRepository(this);

        // Si ya está logueado, ir directamente a MainActivity
        if (authRepository.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
    private void attemptLogin() {
        // TEMPORAL: Usar datos fijos para testing
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        btnLogin.setEnabled(false);
        btnLogin.setText("Iniciando sesión...");

        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                runOnUiThread(() -> {
                    System.out.println("DEBUG LoginActivity - LOGIN EXITOSO");
                    Toast.makeText(LoginActivity.this,
                            "Bienvenido " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    System.out.println("DEBUG LoginActivity - ERROR: " + error);
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");
                });
            }
        });
    }
    /*private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Iniciando sesión...");

        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this,
                            "Bienvenido " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");
                });
            }
        });
    }*/

 //guuardar email usuario
    private void guardarEmailUsuario(String email) {
        getSharedPreferences("UsuarioPrefs", MODE_PRIVATE)
                .edit()
                .putString("user_email", email)
                .apply();
    }
}