package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Ruta;

public class detail_detalles_ruta extends AppCompatActivity {
    private Ruta rut;
    private TextView tvDEstado;
    private TextView tvTiempoRestante;
    private TextView tvHoraSal;
    private TextView tvTarifa;
    private TextView tvDriverNombre;
    private TextView tvRutaOrigen;
    private TextView tvRutaDestino;
    private TextView tvPlaca;
    private ImageButton btnregresar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_detalles_ruta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detallesRuta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponentes();
        asignardetalles();

    }

    private void initComponentes(){
        rut = (Ruta) getIntent().getSerializableExtra("ruta");
        tvDEstado = findViewById(R.id.tvDEstado);
        tvTiempoRestante = findViewById(R.id.tvTiempoRestante);
        tvHoraSal = findViewById(R.id.tvHoraSal);
        tvTarifa = findViewById(R.id.tvTarifa);
        tvDriverNombre = findViewById(R.id.tvDriverNombre);
        tvRutaOrigen = findViewById(R.id.tvRutaOrigen);
        tvRutaDestino = findViewById(R.id.tvRutaDestino);
        tvPlaca = findViewById(R.id.tvPlaca);
        btnregresar = findViewById(R.id.btnregresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void asignardetalles(){
        tvDEstado.setText(rut.getEstado().getNombre());
        long horas=0;
        long min=0;
        try {
            LocalTime horaSalida = rut.getHoraSalida(); // LocalTime
            LocalTime ahora = LocalTime.now();          // Hora actual

            // Extraer hora y minutos si los necesita como int
            int hora = horaSalida.getHour();
            int minutos = horaSalida.getMinute();

            // Calcular tiempo restante en minutos
            long minutosRestantes = ChronoUnit.MINUTES.between(ahora, horaSalida);
             horas = minutosRestantes / 60;
             min = minutosRestantes % 60;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(horas==1) {
            tvTiempoRestante.setText(horas + " hora " + min + " minutos");
        }else {
            tvTiempoRestante.setText(horas+" horas "+min+" minutos");
        }

        tvHoraSal.setText(""+rut.getHoraSalida());
        tvTarifa.setText(rut.getTarifa().toString());
        tvDriverNombre.setText(rut.getMotorista().getUsuario().getNombre());
        tvRutaOrigen.setText(rut.getMunicipioOrigen());
        tvRutaDestino.setText(rut.getMunicipioDestino());
        tvPlaca.setText(rut.getMicrobus().getPlacaVehiculo());


    }

    public void infomotorista(View view) {
        Intent intent = new Intent(detail_detalles_ruta.this, detail_detalles_motorista.class);
        intent.putExtra("motorista", rut.getMotorista());
        startActivity(intent);

    }
}