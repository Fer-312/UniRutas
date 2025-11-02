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
import java.util.List;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.model.PuntoRuta;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;

public class detail_detalles_ruta extends AppCompatActivity {
    private Ruta rut;
    private List<PuntoRuta> puntoRuta;
    private TextView tvDEstado;
    private TextView tvTiempoRestante;
    private TextView tvHoraSal;
    private TextView tvTarifa;
    private TextView tvDriverNombre;
    private TextView tvRutaOrigen;
    private TextView tvRutaDestino;
    private TextView tvPlaca, tvRutalabel, tvinscrito;
    private ImageButton btnregresar;
    private LinearLayout containerDestinos;
    private RutaRepository repository;
    private AuthRepository auth;
    private  boolean inscrito;




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


    }

    private void initComponentes(){
        auth = new AuthRepository(this);
        inscrito=false;
        tvinscrito = findViewById(R.id.tvinscrito);

        puntoRuta = (List<PuntoRuta>) getIntent().getSerializableExtra("puntoRuta");
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
        tvRutalabel = findViewById(R.id.tvRutalabel);
        containerDestinos = findViewById(R.id.containerDestinos);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        repository = new RutaRepository();

        repository.getInscripcionesByRuta(rut.getIdRuta(), new RutaRepository.RepositoryCallback<List<Inscripcion>>() {
            @Override
            public void onSuccess(List<Inscripcion> data) {
                for (Inscripcion item : data) {
                    if(item.getIdEstudiante()== auth.getCurrentEstudianteID()){
                        inscrito=true;
                    }
                }

                asignardetalles();
            }

            @Override
            public void onError(String error) {

            }
        });



    }

    private void asignardetalles(){
        tvDEstado.setText(rut.getEstado().getNombre());
        if ("Disponible".equals(rut.getEstado().getNombre())) {
            tvDEstado.setBackgroundResource(R.drawable.bg_badge_green);
        } else if ("Partió".equals(rut.getEstado().getNombre())) {
            tvDEstado.setBackgroundResource(R.drawable.bg_badge_orange);
        }else{
            tvDEstado.setBackgroundResource(R.drawable.bg_badge_red);
        }

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
        tvRutalabel.setText("Destinos: ");
        int count=0;
        for (PuntoRuta puntoruta: puntoRuta){

            if(puntoruta.getIdRuta()==rut.getIdRuta()){
                // Crear un nuevo TextView por cada punto
                TextView tv = new TextView(this);
                tv.setText(puntoruta.getNombre());
                tv.setTextSize(13);

                // Añadir al contenedor
                containerDestinos.setVisibility(View.VISIBLE);
                containerDestinos.addView(tv);
            }

        }
        if(inscrito){
            tvinscrito.setVisibility(View.VISIBLE);
        }

    }

    public void infomotorista(View view) {
        Intent intent = new Intent(detail_detalles_ruta.this, detail_detalles_motorista.class);
        intent.putExtra("motorista", rut.getMotorista());
        intent.putExtra("idRuta", rut.getIdRuta());
        intent.putExtra("inscritoo", inscrito);
        startActivity(intent);

    }
}