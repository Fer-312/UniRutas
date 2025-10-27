package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Estudiante;
import sv.edu.catolica.unirutas.data.model.Favorito;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.model.Usuario;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.ui.main.auth.LoginActivity;
import sv.edu.catolica.unirutas.utils.ConversionesFecha;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerRutasInscritas;
    private LinearLayout containerRutasFavoritas,sectionsContainer;
    private RutaRepository repository;
    private AuthRepository authRepository;
    private List<Ruta> listRuta;
    private TextView Encabezado;

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
            Encabezado = findViewById(R.id.tvEncabezado);
            Encabezado.setText("Hola, " + usuarioActual.getNombre());

        }

        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        containerRutasFavoritas = findViewById(R.id.containerRutasFavoritas);
        sectionsContainer = findViewById(R.id.sections_container);



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
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();

            }
        });


        Infoperfil();
        RutasPorDestino();

        //Espacio para tabs
        Resources res = getResources();

        TabHost tabControl = (TabHost) findViewById(R.id.MiTabHost);
        tabControl.setup();

        TabHost.TabSpec spec = tabControl.newTabSpec("Home");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_home));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("Rutas");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_bus));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("Perfil");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_user));
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
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();
            }
        });


    }
    private int count=0;
    Calendar ahora = Calendar.getInstance();
    int mesActual = ahora.get(Calendar.MONTH);
    int anioActual = ahora.get(Calendar.YEAR);
    int diaActual = ahora.get(Calendar.DAY_OF_MONTH);
    private double gastado=0;
    private void agregarRutasInscritas() {
        repository.getInscripcionesConRuta(new RutaRepository.RepositoryCallback<List<Inscripcion>>() {
            @Override
            public void onSuccess(List<Inscripcion> data) {
                for(Inscripcion inscripcion: data){

                    if(inscripcion.getIdEstudiante()==authRepository.getCurrentEstudianteID()){


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
                tvGastado.setText(String.valueOf(gastado));
                tvRutasto.setText(String.valueOf(count));

            }

            @Override
            public void onError(String error) {

            }
        });




    }

    private void RutasPorDestino() {
        //showLoading(true); // Reutiliza tu méodo de loading
        //para rutas por origen

        repository.getRutasConEstado(new RutaRepository.RepositoryCallback<List<Ruta>>() {
            @Override
            public void onSuccess(List<Ruta> data) {
                listRuta = data;
                //showLoading(false); // Reutiliza tu méodo de loading



                String municipioAnterior="";
                //Agregamos un contenedor con su titulo:
                for (Ruta ruta : data) {
                    Calendar fecha = ConversionesFecha.convertirStringACalendar(ruta.getFechaCreacion());
                    if(fecha.get(Calendar.YEAR)==anioActual && fecha.get(Calendar.MONTH)==mesActual && fecha.get(Calendar.DAY_OF_MONTH)==diaActual){
                        if(!ruta.getMunicipioOrigen().equals("Unicaes")){
                            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_municipio, sectionsContainer, false);
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
                                sectionsContainer.addView(itemView);
                                municipioAnterior=ruta.getMunicipioOrigen();


                                for (Ruta ruta2 : data){
                                    Calendar fecha2 = ConversionesFecha.convertirStringACalendar(ruta2.getFechaCreacion());
                                    if(fecha2.get(Calendar.YEAR)==anioActual && fecha2.get(Calendar.MONTH)==mesActual && fecha2.get(Calendar.DAY_OF_MONTH)==diaActual){
                                        if(ruta2.getMunicipioOrigen().equals(municipioAnterior)){
                                            View itemView2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasAgrupadas, false);
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
                municipioAnterior="";

                //aqui la segunda paprte que es ordenarlo por destino
                for (Ruta ruta : data) {
                    Calendar fecha = ConversionesFecha.convertirStringACalendar(ruta.getFechaCreacion());
                    if(fecha.get(Calendar.YEAR)==anioActual && fecha.get(Calendar.MONTH)==mesActual && fecha.get(Calendar.DAY_OF_MONTH)==diaActual){
                        if(!ruta.getMunicipioDestino().equals("Unicaes")){
                            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_municipio, sectionsContainer, false);
                            LinearLayout header = itemView.findViewById(R.id.headerSection);

                            TextView tv_section_titulo = itemView.findViewById(R.id.tv_section_titulo);
                            tv_section_titulo.setText(ruta.getMunicipioDestino()+" (Salida)");


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

                            if (!ruta.getMunicipioDestino().equals(municipioAnterior) ){
                                sectionsContainer.addView(itemView);
                                municipioAnterior=ruta.getMunicipioDestino();


                                for (Ruta ruta2 : data){
                                    Calendar fecha2 = ConversionesFecha.convertirStringACalendar(ruta2.getFechaCreacion());
                                    if(fecha2.get(Calendar.YEAR)==anioActual && fecha2.get(Calendar.MONTH)==mesActual && fecha2.get(Calendar.DAY_OF_MONTH)==diaActual){
                                        if(ruta2.getMunicipioDestino().equals(municipioAnterior)){
                                            View itemView2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasAgrupadas, false);
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
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();
            }
        });

        //Para rutas por destino

    }

    private void Infoperfil(){
        Usuario user = authRepository.getCurrentUser();
        TextView tvNombre = findViewById(R.id.txtNombre);
        TextView tvCorreo = findViewById(R.id.tvCorreo);
        TextView tvTelefono = findViewById(R.id.tvTelefono);

        tvNombre.setText(user.getNombre());
        tvCorreo.setText(user.getCorreo());
        tvTelefono.setText(user.getTelefono());

    }

    public   void Logout(View v){
        authRepository.logout();
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onRutaCardClick(Ruta ruta) {
        Intent intent = new Intent(MainActivity.this, detail_detalles_ruta.class);
        intent.putExtra("ruta", ruta);
        startActivity(intent);
    }


}