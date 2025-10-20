package sv.edu.catolica.unirutas.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.model.Estudiante;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.utils.Constants;

import java.util.List;

public class main2 extends AppCompatActivity {

    private Button btnTestConnection, btnGetRutas, btnGetEstudiantes;
    private ProgressBar progressBar;
    private TextView tvResult, tvCount;
    private RutaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        initViews();
        repository = new RutaRepository();

        setupClickListeners();

    }


    private void initViews() {
        btnTestConnection = findViewById(R.id.btnTestConnection);
        btnGetRutas = findViewById(R.id.btnGetRutas);
        btnGetEstudiantes = findViewById(R.id.btnGetEstudiantes);
        progressBar = findViewById(R.id.progressBar);
        tvResult = findViewById(R.id.tvResult);
        tvCount = findViewById(R.id.tvCount);
    }

    private void setupClickListeners() {
        btnTestConnection.setOnClickListener(v -> testConnection());
        btnGetRutas.setOnClickListener(v -> getRutas());
        btnGetEstudiantes.setOnClickListener(v -> getEstudiantes());
    }

    private void testConnection() {
        showLoading(true);
        tvResult.setText("Probando conexión con Supabase...");

        // Probar obteniendo rutas como test de conexión
        repository.getRutas(new RutaRepository.RepositoryCallback<List<Ruta>>() {
            @Override
            public void onSuccess(List<Ruta> rutas) {
                showLoading(false);
                tvResult.setText("✅ Conexión exitosa!\n\nSe encontraron " + rutas.size() + " rutas.");
                tvCount.setText("Resultados: " + rutas.size());

                // Mostrar primeras 3 rutas como ejemplo
                StringBuilder sb = new StringBuilder();
                sb.append("✅ Conexión exitosa!\n\n");
                sb.append("Primeras " + Math.min(3, rutas.size()) + " rutas:\n\n");

                for (int i = 0; i < Math.min(3, rutas.size()); i++) {
                    Ruta ruta = rutas.get(i);
                    sb.append("Ruta ").append(i + 1).append(":\n");
                    sb.append("• Origen: ").append(ruta.getMunicipioOrigen()).append("\n");
                    sb.append("• Destino: ").append(ruta.getMunicipioDestino()).append("\n");
                    sb.append("• Tarifa: $").append(ruta.getTarifa()).append("\n");
                    sb.append("• Hora: ").append(ruta.getHoraSalida()).append("\n\n");
                }

                tvResult.setText(sb.toString());
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                tvResult.setText("❌ Error de conexión:\n" + error);
                tvCount.setText("Resultados: 0");
                Toast.makeText(main2.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRutas() {
        showLoading(true);
        tvResult.setText("Obteniendo rutas...");

        repository.getRutas(new RutaRepository.RepositoryCallback<List<Ruta>>() {
            @Override
            public void onSuccess(List<Ruta> rutas) {
                showLoading(false);
                StringBuilder sb = new StringBuilder();
                sb.append("📋 Lista de Rutas (").append(rutas.size()).append("):\n\n");

                for (int i = 0; i < rutas.size(); i++) {
                    Ruta ruta = rutas.get(i);
                    sb.append("🚌 Ruta ").append(i + 1).append(":\n");
                    sb.append("ID: ").append(ruta.getIdRuta()).append("\n");
                    sb.append("Origen: ").append(ruta.getMunicipioOrigen()).append("\n");
                    sb.append("Destino: ").append(ruta.getMunicipioDestino()).append("\n");
                    sb.append("Tarifa: $").append(ruta.getTarifa()).append("\n");
                    sb.append("Hora Salida: ").append(ruta.getHoraSalida()).append("\n");
                    sb.append("Capacidad: ").append(ruta.getCapacidadTotal()).append("\n");
                    sb.append("Fcreacion: ").append(ruta.getFechaCreacion()).append("\n");
                    sb.append("--------------------\n\n");
                }

                tvResult.setText(sb.toString());
                tvCount.setText("Rutas encontradas: " + rutas.size());
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                tvResult.setText("❌ Error al obtener rutas:\n" + error);
                tvCount.setText("Rutas encontradas: 0");
            }
        });
    }

    private void getEstudiantes() {
        showLoading(true);
        tvResult.setText("Obteniendo estudiantes...");

        repository.getEstudiantes(new RutaRepository.RepositoryCallback<List<Estudiante>>() {
            @Override
            public void onSuccess(List<Estudiante> estudiantes) {
                showLoading(false);
                StringBuilder sb = new StringBuilder();
                sb.append("👨‍🎓 Lista de Estudiantes (").append(estudiantes.size()).append("):\n\n");

                for (int i = 0; i < estudiantes.size(); i++) {
                    Estudiante estudiante = estudiantes.get(i);
                    sb.append("Estudiante ").append(i + 1).append(":\n");
                    sb.append("ID: ").append(estudiante.getIdEstudiante()).append("\n");
                    sb.append("Carnet: ").append(estudiante.getCarnet()).append("\n");
                    sb.append("Universidad: ").append(estudiante.getUniversidad()).append("\n");
                    sb.append("--------------------\n\n");
                }

                tvResult.setText(sb.toString());
                tvCount.setText("Estudiantes encontrados: " + estudiantes.size());
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                tvResult.setText("❌ Error al obtener estudiantes:\n" + error);
                tvCount.setText("Estudiantes encontrados: 0");
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnTestConnection.setEnabled(!show);
        btnGetRutas.setEnabled(!show);
        btnGetEstudiantes.setEnabled(!show);
    }
}