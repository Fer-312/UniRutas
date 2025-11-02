package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.model.PuntoRuta;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.utils.ConversionesFecha;

public class Ver_HistorialRuta_usuario extends AppCompatActivity {

    private RutaRepository repository;
    private LinearLayout containerRutasInscritas;
    private AuthRepository authRepository;
    private List<PuntoRuta> puntoRuta;
    private List<PuntoRuta> puntoRutaFiltrada = new ArrayList<>();
    private ImageButton btnregresar;
    Calendar ahora = Calendar.getInstance();;
    int mesActual = ahora.get(Calendar.MONTH);
    int anioActual = ahora.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ver_historial_ruta_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Ver_HistorialRuta_usuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponentes();
    }
    private void initComponentes(){
        repository = new RutaRepository();
        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        btnregresar = findViewById(R.id.btnregresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        authRepository = new AuthRepository(this);
        CargarDestinos();
    }


    private void agregarRutasInscritas() {
        repository.getInscripcionesConRuta(new RutaRepository.RepositoryCallback<List<Inscripcion>>() {
            @Override
            public void onSuccess(List<Inscripcion> data) {
                for(Inscripcion inscripcion: data){
                    if(inscripcion.getFechaAsistencia()!=null){
                        Calendar cal = ConversionesFecha.convertirStringACalendar(inscripcion.getFechaAsistencia());
                        Calendar cal1 = ConversionesFecha.convertirStringACalendar(inscripcion.getRuta().getFechaCreacion());
                        if(inscripcion.getIdEstudiante()==authRepository.getCurrentEstudianteID() ){
                            if(cal1.get(Calendar.YEAR)==anioActual && cal1.get(Calendar.MONTH)==mesActual){
                                View itemView = LayoutInflater.from(Ver_HistorialRuta_usuario.this).inflate(R.layout.item_ruta, containerRutasInscritas, false);
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
                                asignarDestionos(inscripcion.getRuta().getIdRuta(),itemView);


                                //este es el boton que abre la ruta
                                itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onRutaCardClick(inscripcion.getRuta());
                                    }
                                });

                                // Cambiar color según el estado

                                if ("Disponible".equals(inscripcion.getRuta().getEstado().getNombre())) {
                                    tvEstado.setBackgroundResource(R.drawable.bg_badge_green);
                                    containerRutasInscritas.addView(itemView);
                                } else if ("Partió".equals(inscripcion.getRuta().getEstado().getNombre())) {
                                    tvEstado.setBackgroundResource(R.drawable.bg_badge_orange);
                                    containerRutasInscritas.addView(itemView);
                                }else{
                                    tvEstado.setBackgroundResource(R.drawable.bg_badge_red);
                                    containerRutasInscritas.addView(itemView);
                                }
                            }




                        }
                    }

                }


            }

            @Override
            public void onError(String error) {

            }
        });
    }


    //==================== Metodos Para cargar destinos y asignarlos ====================================
    private void CargarDestinos(){
        repository.getPuntosRutaDeMotoristas(new RutaRepository.RepositoryCallback<List<PuntoRuta>>() {
            @Override
            public void onSuccess(List<PuntoRuta> data) {
                puntoRuta= data;
                agregarRutasInscritas();
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private  void asignarDestionos(int idRuta,View itemView2){
        TextView tvRutalabel = itemView2.findViewById(R.id.tvRutalabel);
        tvRutalabel.setText("Destinos: ");
        LinearLayout containerDestinos = itemView2.findViewById(R.id.containerDestinos);
        for (PuntoRuta puntoRuta1: puntoRuta) {
            if(puntoRuta1.getIdRuta()==idRuta){
                boolean existe = false;
                for (PuntoRuta pr : puntoRutaFiltrada) {
                    if (pr.getIdPunto() == puntoRuta1.getIdPunto()) {
                        existe = true;
                        break;
                    }
                }
                if (!existe){
                    puntoRutaFiltrada.add(puntoRuta1);

                }
                // Crear un nuevo TextView por cada punto
                TextView tv = new TextView(itemView2.getContext());
                tv.setText(puntoRuta1.getNombre());
                tv.setTextSize(14);

                // Añadir al contenedor
                containerDestinos.setVisibility(View.VISIBLE);
                containerDestinos.addView(tv);

            }
        }
    }

    //==================== Metodos de Navegacion ====================================
    private void onRutaCardClick(Ruta ruta) {
        Intent intent = new Intent(Ver_HistorialRuta_usuario.this, detail_detalles_ruta.class);
        intent.putExtra("ruta", ruta);
        intent.putExtra("puntoRuta", (Serializable) puntoRutaFiltrada);
        startActivity(intent);
    }
}