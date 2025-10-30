package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import sv.edu.catolica.unirutas.data.model.PuntoRuta;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.utils.ConversionesFecha;

public class detail_detalles_Horarios extends AppCompatActivity {
    private LinearLayout containerRutasInscritas2, containerRutasInscritas;
    private TextView tvHorarioGeneral;
    private int idHorariogeneral;
//Se podrian mandar no hacer la consulta
    private List<PuntoRuta> puntoRutaFiltrada = new ArrayList<>();
    private List<PuntoRuta> puntoRuta;


    private RutaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_detalles_horarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_detalles_horarios), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponentes();
    }
    Calendar ahora = Calendar.getInstance();;
    int mesActual = ahora.get(Calendar.MONTH);
    int anioActual = ahora.get(Calendar.YEAR);
    int diaActual = ahora.get(Calendar.DAY_OF_MONTH);

    private void initComponentes(){
        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        containerRutasInscritas2 = findViewById(R.id.containerRutasInscritas2);
        tvHorarioGeneral = findViewById(R.id.tvHorarioGeneral);
        idHorariogeneral = getIntent().getIntExtra("idHorariogeneral", 0);
        tvHorarioGeneral.setText(getIntent().getStringExtra("hora"));
        repository = new RutaRepository();
        cargarinfo(idHorariogeneral);



    }
    private void CargarDestinos(){
        repository.getPuntosRutaDeMotoristas(new RutaRepository.RepositoryCallback<List<PuntoRuta>>() {
            @Override
            public void onSuccess(List<PuntoRuta> data) {
                puntoRuta= data;
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void cargarinfo(int idHorario){
        repository.getRutasConEstadoyMotorista(idHorario, new RutaRepository.RepositoryCallback<List<Ruta>>() {
            @Override
            public void onSuccess(List<Ruta> data) {
                String municipioAnterior="";
                //Agregamos un contenedor con su titulo:
                for (Ruta ruta : data) {
                    Calendar fecha = ConversionesFecha.convertirStringACalendar(ruta.getFechaCreacion());
                    if(fecha.get(Calendar.YEAR)==anioActual && fecha.get(Calendar.MONTH)==mesActual && fecha.get(Calendar.DAY_OF_MONTH)==diaActual){
                        if(!ruta.getMunicipioOrigen().equals("Unicaes")){

                            View itemView = LayoutInflater.from(detail_detalles_Horarios.this).inflate(R.layout.item_municipio, containerRutasInscritas, false);
                            LinearLayout header = itemView.findViewById(R.id.headerSection);

                            TextView tv_section_titulo = itemView.findViewById(R.id.tv_section_titulo);
                            tv_section_titulo.setText(ruta.getMunicipioOrigen()+" (Entrada)");


                            LinearLayout containerRutasAgrupadas = itemView.findViewById(R.id.containerRutasAgrupadas);
                            ImageView arrow = itemView.findViewById(R.id.iv_arrow);
                            header.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (containerRutasAgrupadas.getVisibility() == View.VISIBLE) {
                                        containerRutasAgrupadas.setVisibility(View.GONE);
                                        arrow.setRotation(0);
                                    } else {
                                        containerRutasAgrupadas.setVisibility(View.VISIBLE);
                                        arrow.setRotation(180);
                                    }
                                }
                            });

                            if (!ruta.getMunicipioOrigen().equals(municipioAnterior) ){
                                containerRutasInscritas.addView(itemView);
                                municipioAnterior=ruta.getMunicipioOrigen();


                                for (Ruta ruta2 : data){
                                    Calendar fecha2 = ConversionesFecha.convertirStringACalendar(ruta2.getFechaCreacion());
                                    if(fecha2.get(Calendar.YEAR)==anioActual && fecha2.get(Calendar.MONTH)==mesActual && fecha2.get(Calendar.DAY_OF_MONTH)==diaActual){
                                        if(ruta2.getMunicipioOrigen().equals(municipioAnterior)){
                                            View itemView2 = LayoutInflater.from(detail_detalles_Horarios.this).inflate(R.layout.item_ruta, containerRutasInscritas2, false);
                                            TextView tvRutaOrigen = itemView2.findViewById(R.id.tvRutaOrigen);
                                            TextView tvRutaDestino = itemView2.findViewById(R.id.tvRutaDestino);
                                            TextView tvHorario = itemView2.findViewById(R.id.tvHorario);
                                            TextView tvEstado = itemView2.findViewById(R.id.tvEstado);
                                            LinearLayout liMotorista = itemView2.findViewById(R.id.motorista_container);
                                            liMotorista.setVisibility(View.VISIBLE);

                                            TextView tvMotorista = itemView2.findViewById(R.id.tvMotoristaName);
                                            tvMotorista.setText(ruta2.getMotorista().getUsuario().getNombre());
                                            tvRutaOrigen.setText(ruta2.getMunicipioOrigen());
                                            tvRutaDestino.setText(ruta2.getMunicipioDestino());
                                            tvHorario.setText(""+ruta2.getHoraSalida());
                                            asignarDestionos(ruta2.getIdRuta(),itemView2);

                                            itemView2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    onRutaCardClick(ruta2);
                                                }
                                            });

                                            tvEstado.setText(ruta2.getEstado().getNombre());
                                            if ("Disponible".equals(ruta2.getEstado().getNombre())) {
                                                tvEstado.setBackgroundResource(R.drawable.bg_badge_green);
                                            } else if ("Partió".equals(ruta2.getEstado().getNombre())) {
                                                tvEstado.setBackgroundResource(R.drawable.bg_badge_orange);
                                            }else{
                                                tvEstado.setBackgroundResource(R.drawable.bg_badge_red);
                                            }
                                            tvEstado.setTextColor(getResources().getColor(R.color.white));
                                            containerRutasAgrupadas.addView(itemView2);
                                        }
                                    }
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
    private  void asignarDestionos(int idRuta,View itemView2){
        TextView tvRutalabel = itemView2.findViewById(R.id.tvRutalabel);
        tvRutalabel.setText("Destinos: ");
        LinearLayout containerDestinos = itemView2.findViewById(R.id.containerDestinos);
        for (PuntoRuta puntoRuta1: puntoRuta) {
            if(puntoRuta1.getIdRuta()==idRuta){
                puntoRutaFiltrada.add(puntoRuta1);
                // Crear un nuevo TextView por cada punto
                TextView tv = new TextView(itemView2.getContext());
                tv.setText(puntoRuta1.getNombre());
                tv.setTextSize(13);

                // Añadir al contenedor
                containerDestinos.setVisibility(View.VISIBLE);
                containerDestinos.addView(tv);
            }
        }
    }
    private void onRutaCardClick(Ruta ruta) {
        Intent intent = new Intent(detail_detalles_Horarios.this, detail_detalles_ruta.class);
        intent.putExtra("ruta", ruta);
        intent.putExtra("puntoRuta", (Serializable) puntoRutaFiltrada);
        startActivity(intent);
    }


}