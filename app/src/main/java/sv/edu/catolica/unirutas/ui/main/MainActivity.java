package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Estudiante;
import sv.edu.catolica.unirutas.data.model.Favorito;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.model.Ruta1;
import sv.edu.catolica.unirutas.data.model.Usuario;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.ui.main.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerRutasInscritas;
    private LinearLayout containerRutasFavoritas;
    private RutaRepository repository;
    private AuthRepository authRepository;

    private TextView tvRutasto, tvGastado;

    private ProgressBar progressBar;
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        containerRutasInscritas.setEnabled(!show);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        authRepository = new AuthRepository(this);
        if (!authRepository.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Mostrar datos del usuario
        Usuario usuarioActual = authRepository.getCurrentUser();
        if (usuarioActual != null) {
            TextView Encabezado = findViewById(R.id.tvEncabezado);
            Encabezado.setText("Hola " + usuarioActual.getNombre());

        }

        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        containerRutasFavoritas = findViewById(R.id.containerRutasFavoritas);

        repository = new RutaRepository();

        // Agregar rutas
        repository.getEstudianteByIdUsuario(authRepository.getCurrentUser().getIdUsuario(), new RutaRepository.RepositoryCallback<List<Estudiante>>() {
            @Override
            public void onSuccess(List<Estudiante> data) {
                for (Estudiante estudiante:data) {
                    authRepository.saveUserData(estudiante.getIdEstudiante());

                    Toast.makeText(MainActivity.this,String.valueOf(authRepository.getCurrentEstudianteID()),Toast.LENGTH_LONG).show();
                }
                tvGastado = findViewById(R.id.tvGastado);
                tvRutasto = findViewById(R.id.tvTotalRutas);
                agregarRutasFavoritas();
                agregarRutasInscritas();
            }

            @Override
            public void onError(String error) {

            }
        });


        //Espacio para tabs
        Resources res = getResources();

        TabHost tabControl = (TabHost) findViewById(R.id.MiTabHost);
        tabControl.setup();

        TabHost.TabSpec spec = tabControl.newTabSpec("PESO");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_home));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("TEMP");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_bus));
        tabControl.addTab(spec);

    }


    private void agregarRutasFavoritas() {
        repository.getFavoritosByUsuario(authRepository.getCurrentUser().getIdUsuario(),new RutaRepository.RepositoryCallback<List<Favorito>>() {
            @Override
            public void onSuccess(List<Favorito> data) {
                for(Favorito favorito: data){

                        View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasFavoritas, false);
                        TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
                        TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
                        TextView tvHorario = itemView.findViewById(R.id.tvHorario);
                        TextView tvEstado = itemView.findViewById(R.id.tvEstado);

                        tvEstado.setVisibility(View.GONE);
                        tvRutaOrigen.setText("Rutas ");
                        tvHorario.setVisibility(View.GONE);



                    tvRutaDestino.setText(""+favorito.getHorario().getHora());


                        //este es el boton que abre la ruta
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //onRutaCardClick(favorito.getRuta());
                            }
                        });
                        containerRutasFavoritas.addView(itemView);



                }

            }

            @Override
            public void onError(String error) {

            }
        });


    }
    private int count=0;
    private double gastado=0;
    private void agregarRutasInscritas() {
        repository.getInscripcionesConRuta(new RutaRepository.RepositoryCallback<List<Inscripcion>>() {
            @Override
            public void onSuccess(List<Inscripcion> data) {
                for(Inscripcion inscripcion: data){

                    if(inscripcion.getIdEstudiante()==authRepository.getCurrentEstudianteID()){
                        Calendar ahora = Calendar.getInstance();
                        int mesActual = ahora.get(Calendar.MONTH);
                        int anioActual = ahora.get(Calendar.YEAR);

                        try {
                            String fechaStr = inscripcion.getFechaAsistencia(); // "18/10/2025 05:28:11"
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            Date fecha = sdf.parse(fechaStr);

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(fecha);

                            int mes = cal.get(Calendar.MONTH);
                            int anio = cal.get(Calendar.YEAR);

                            if (mes == mesActual && anio == anioActual) {
                                count++;
                                gastado+=Double.parseDouble(inscripcion.getTarifaPagada().toString()) ;

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasInscritas, false);
                        TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
                        TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
                        TextView tvHorario = itemView.findViewById(R.id.tvHorario);
                        TextView tvEstado = itemView.findViewById(R.id.tvEstado);

                        tvRutaOrigen.setText("Ruta " + inscripcion.getRuta().getMunicipioOrigen());
                        tvRutaDestino.setText(inscripcion.getRuta().getMunicipioDestino());
                        tvHorario.setText(String.valueOf(inscripcion.getRuta().getHoraSalida()));
                        //Configuraciones de interfaz como color etc
                        tvEstado.setTextColor(getResources().getColor(R.color.white));
                        tvEstado.setText(inscripcion.getRuta().getEstado().getNombre());


                        //este es el boton que abre la ruta
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRutaCardClick(inscripcion.getRuta());
                            }
                        });

                        // Cambiar color según el estado
                        if ("Lleno".equals(inscripcion.getRuta().getEstado().getNombre())) {
                            tvEstado.setBackgroundResource(R.drawable.bg_badge_red);
                            containerRutasInscritas.addView(itemView);

                        } else if ("Disponible".equals(inscripcion.getRuta().getEstado().getNombre())) {
                            tvEstado.setBackgroundResource(R.drawable.bg_badge_green);
                            containerRutasInscritas.addView(itemView);
                        }

                    }
                }
                tvGastado.setText(String.valueOf(gastado));
                tvRutasto.setText(String.valueOf(count));

            }

            @Override
            public void onError(String error) {

            }
        });




    }


    public   void Logout(View v){
        authRepository.logout();
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void onRutaCardClick(Ruta ruta) {
        // Aquí pones la lógica cuando se pulsa una card

        Toast.makeText(this,
                "Card pulsada: " + ruta.getMunicipioOrigen() + " → " + ruta.getMunicipioDestino() +
                        "\nHorario: " + ruta.getHoraSalida() +
                        "\nEstado: " + ruta.getEstado().getNombre(),
                Toast.LENGTH_LONG).show();
    }


}