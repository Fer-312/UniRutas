package sv.edu.catolica.unirutas.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Estudiante;
import sv.edu.catolica.unirutas.data.model.Favorito;
import sv.edu.catolica.unirutas.data.model.Horario;
import sv.edu.catolica.unirutas.data.model.Inscripcion;
import sv.edu.catolica.unirutas.data.model.Motorista;
import sv.edu.catolica.unirutas.data.model.PuntoRuta;
import sv.edu.catolica.unirutas.data.model.Ruta;
import sv.edu.catolica.unirutas.data.model.Usuario;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.ui.main.auth.LoginActivity;
import sv.edu.catolica.unirutas.utils.ConversionesFecha;
import sv.edu.catolica.unirutas.utils.FileUtils;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerRutasInscritas,containerRutasFavoritas,sectionsContainer,containerRutasFavoritas2,containerHorariosF;
    private RutaRepository repository;
    private AuthRepository authRepository;
    private List<Ruta> listRuta;
    private List<Favorito> listfavoritosUsuario;
    private TextView Encabezado;
    private List<PuntoRuta> puntoRuta, puntoRutaFiltrada = new ArrayList<>();
    private List<Integer> idFavoritos = new ArrayList<>();

    //==================== Variables para resumen ====================================
    private int count=0;
    private double gastado=0;

    //=========================== Variables de Tiempo =====================
    Calendar ahora = Calendar.getInstance();;
    int mesActual = ahora.get(Calendar.MONTH);
    int anioActual = ahora.get(Calendar.YEAR);
    int diaActual = ahora.get(Calendar.DAY_OF_MONTH);

    //==================== Variables de pestaña del Perfil ====================================
    private Button btnEditarPerfil,btnHistorial;
    private TextView tvRutasto, tvGastado;

    //==================== Posibiilidad de boton cargando(No en uso) ====================================
    private ProgressBar progressBar;
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        containerRutasInscritas.setEnabled(!show);
    }

    //==================== Metodos onCreate ====================================
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
        //========================inicia aqui ========================
        /*repository.getMotoristaByIdUsuario(authRepository.getCurrentUser().getIdUsuario(), new RutaRepository.RepositoryCallback<List<Motorista>>() {
            @Override
            public void onSuccess(List<Motorista> data) {
                for (Motorista motorista:data){
                    authRepository.saveUserDataMotorista(motorista.getIdMotorista());


                }
            }

            @Override
            public void onError(String error) {

            }
        });
        */
        initComponentes();
        CargarDestinos();



        repository.getEstudianteByIdUsuario(authRepository.getCurrentUser().getIdUsuario(), new RutaRepository.RepositoryCallback<List<Estudiante>>() {
            @Override
            public void onSuccess(List<Estudiante> data) {
                for (Estudiante estudiante:data) {
                    authRepository.saveUserData(estudiante.getIdEstudiante());
                }
                Dashboard_AgregarRutasFavoritas();

            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();

            }
        });

        Infoperfil();
        initTabhost();
    }
    //==================== Metodos al Inicializar ALL =============================================
    private void initComponentes(){
        repository = new RutaRepository();
        tvGastado = findViewById(R.id.tvGastado);
        tvRutasto = findViewById(R.id.tvTotalRutas);
        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        containerRutasFavoritas = findViewById(R.id.containerRutasFavoritas);
        sectionsContainer = findViewById(R.id.sections_container);
        containerRutasFavoritas2 = findViewById(R.id.containerRutasFavoritas2);
        containerHorariosF = findViewById(R.id.containerHorariosF);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        btnHistorial = findViewById(R.id.btnHistorial);


        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intet = new Intent(MainActivity.this, detail_editar_perfil.class);
                startActivity(intet);
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intet = new Intent(MainActivity.this, Ver_HistorialRuta_usuario.class);
                startActivity(intet);
            }
        });

    }

    private void initTabhost(){
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

        spec = tabControl.newTabSpec("Favoritos");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_star));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("Perfil");
        spec.setContent(R.id.tab4);
        spec.setIndicator("",res.getDrawable(R.drawable.ic_user));
        tabControl.addTab(spec);
    }

    private void CargarDestinos(){
        repository.getPuntosRutaDeMotoristas(new RutaRepository.RepositoryCallback<List<PuntoRuta>>() {
            @Override
            public void onSuccess(List<PuntoRuta> data) {
                puntoRuta= data;
                RutasPorDestino();
                agregarRutasInscritas();
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    //==================== Logica para regreso y actualización de datos Main ====================================

    @Override
    protected void onResume() {
        super.onResume();
        Usuario user = authRepository.getCurrentUser();
        Infoperfil();
        Encabezado.setText("Hola, " + user.getNombre());
    }

    //==================== Metodos Para pestaña Favoritos ====================================
    private void agregarPestañaFavoritos(){
        for(Favorito favorito: listfavoritosUsuario) {
            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasFavoritas2, false);
            TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
            TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
            TextView tvHorario = itemView.findViewById(R.id.tvHorario);
            TextView tvEstado = itemView.findViewById(R.id.tvEstado);
            tvEstado.setVisibility(View.GONE);
            tvRutaOrigen.setText("Rutas para hora clase ");
            tvHorario.setVisibility(View.GONE);
            tvRutaDestino.setText(""+favorito.getHorario().getHora());

            //este es el boton que abre la ruta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavoritoCardClick(favorito.getIdHora(),favorito.getHorario().getHora().toString());
                }
            });
            containerRutasFavoritas2.addView(itemView);
        }
        repository.getHorarios(new RutaRepository.RepositoryCallback<List<Horario>>() {
            @Override
            public void onSuccess(List<Horario> data) {
                for (Horario hora : data) {
                    View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerHorariosF, false);
                    TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
                    TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
                    TextView tvHorario = itemView.findViewById(R.id.tvHorario);
                    TextView tvEstado = itemView.findViewById(R.id.tvEstado);
                    tvEstado.setVisibility(View.GONE);
                    tvRutaOrigen.setText("Rutas para hora clase ");
                    tvHorario.setVisibility(View.GONE);
                    tvRutaDestino.setText(""+hora.getHora());

                    //este es el boton que abre la ruta
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onFavoritoCardClick(hora.getIdHora(),hora.getHora().toString());
                        }
                    });
                    int count3=0;
                    for (int i = 0; i < idFavoritos.size(); i++) {
                        if(hora.getIdHora()==idFavoritos.get(i)){
                            count3++;
                        }
                    }
                    if(count3==0){
                        containerHorariosF.addView(itemView);
                    }



                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    //==================== Metodos Dashboard ====================================
    //-------------------- Metodos Para Seccion Favoritos del Dashboard -----------------------------------
    private void Dashboard_AgregarRutasFavoritas() {
        repository.getFavoritosByUsuario(authRepository.getCurrentUser().getIdUsuario(),new RutaRepository.RepositoryCallback<List<Favorito>>() {
            @Override
            public void onSuccess(List<Favorito> data) {
                listfavoritosUsuario = data;
                agregarPestañaFavoritos();
                for(Favorito favorito: data){
                    idFavoritos.add(favorito.getIdHora());
                        View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ruta, containerRutasFavoritas, false);
                        TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
                        TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
                        TextView tvHorario = itemView.findViewById(R.id.tvHorario);
                        TextView tvEstado = itemView.findViewById(R.id.tvEstado);

                        tvEstado.setVisibility(View.GONE);
                        tvRutaOrigen.setText("Rutas de hora clase ");
                        tvHorario.setVisibility(View.GONE);



                    tvRutaDestino.setText(""+favorito.getHorario().getHora());


                        //este es el boton que abre la ruta
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onFavoritoCardClick(favorito.getIdHora(),favorito.getHorario().getHora().toString());
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
    //-------------------- Metodos Para Seccion Favoritos del Dashboard -----------------------------------
    private void agregarRutasInscritas() {
        repository.getInscripcionesConRuta(new RutaRepository.RepositoryCallback<List<Inscripcion>>() {
            @Override
            public void onSuccess(List<Inscripcion> data) {
                for(Inscripcion inscripcion: data){
                    Calendar cal1 = ConversionesFecha.convertirStringACalendar(inscripcion.getRuta().getFechaCreacion());
                    if(inscripcion.getIdEstudiante()==authRepository.getCurrentEstudianteID() ){
                        try {
                            if(inscripcion.getFechaAsistencia()!=null){
                                Calendar cal = ConversionesFecha.convertirStringACalendar(inscripcion.getFechaAsistencia());
                                int mes = cal.get(Calendar.MONTH);
                                int anio = cal.get(Calendar.YEAR);
                                if (mes == mesActual && anio == anioActual) {
                                    count++;
                                    gastado+=Double.parseDouble(inscripcion.getTarifaPagada().toString()) ;

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(cal1.get(Calendar.YEAR)==anioActual && cal1.get(Calendar.MONTH)==mesActual && cal1.get(Calendar.DAY_OF_MONTH)==diaActual){
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
                tvGastado.setText(String.valueOf(gastado));
                tvRutasto.setText(String.valueOf(count));

            }

            @Override
            public void onError(String error) {

            }
        });
    }


    //==================== Metodos Para pestaña Perfil ====================================
    private void Infoperfil(){
        Usuario user = authRepository.getCurrentUser();
        TextView tvNombre = findViewById(R.id.txtNombre);
        TextView tvCorreo = findViewById(R.id.tvCorreo);
        TextView tvTelefono = findViewById(R.id.tvTelefono);
        ImageView imgPerfil = findViewById(R.id.imgPerfil);

        //mi metodo del Glide
        FileUtils.MostrarImagen(MainActivity.this,imgPerfil,user.getFotoPerfil());
        tvNombre.setText(user.getNombre());
        tvCorreo.setText(user.getCorreo());
        tvTelefono.setText(user.getTelefono());

    }


    //==================== Metodos pestaña Rutas ====================================
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
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();
            }
        });

        //Para rutas por destino

    }


    //==================== Metodos Auxiliares ====================================
    public   void Logout(View v){
        authRepository.logout();
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(MainActivity.this, detail_detalles_ruta.class);
        intent.putExtra("ruta", ruta);
        intent.putExtra("puntoRuta", (Serializable) puntoRutaFiltrada);
        startActivity(intent);
    }

    private void onFavoritoCardClick(int idHorario,String hora) {
        Intent intent = new Intent(MainActivity.this, detail_detalles_Horarios.class);
        intent.putExtra("idHorariogeneral", idHorario);
        intent.putExtra("hora", hora);
        startActivity(intent);
    }


}