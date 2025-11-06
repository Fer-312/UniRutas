package sv.edu.catolica.unirutas.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Comentario;
import sv.edu.catolica.unirutas.data.model.Motorista;
import sv.edu.catolica.unirutas.data.repository.AuthRepository;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;
import sv.edu.catolica.unirutas.utils.FileUtils;

public class detail_detalles_motorista extends AppCompatActivity {
    private Motorista motorista;
    private TextView tvMotoristaName;
    private TextView tvRate,ComentariosRecientes;
    private TextView tvDiasService;
    private TextView tvTelefono;
    private TextView tvEmail;
    private ImageButton btnregresar;
    private RutaRepository repository;
    private AuthRepository authRepository;
    private LinearLayout containerComentarios,linearañadircomentarioInscrito;
    private Button btnVerMas, ComentarButton;
    private int idRuta;
    private RatingBar ratingBar;
    private TextView tvValorRating;
    private boolean inscrito;
    private ImageView profile_image;

    private EditText etComentario;
    private List<Comentario> coments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_detalles_motorista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_detalles_motorista), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponentes();
        asignardetalles();

    }
    private void initComponentes(){
        authRepository = new AuthRepository(detail_detalles_motorista.this);
        motorista = (Motorista) getIntent().getSerializableExtra("motorista");
        idRuta = getIntent().getIntExtra("idRuta",0);
        inscrito = getIntent().getBooleanExtra("inscritoo",false);
        linearañadircomentarioInscrito = findViewById(R.id.linearañadircomentarioInscrito);
        if(inscrito){
            linearañadircomentarioInscrito.setVisibility(View.VISIBLE);
        }

        tvMotoristaName = findViewById(R.id.tvMotoristaName);
        profile_image = findViewById(R.id.profile_image);
        tvRate = findViewById(R.id.tvRate);
        tvDiasService = findViewById(R.id.tvDiasService);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvEmail = findViewById(R.id.tvEmail);
        btnregresar = findViewById(R.id.btnregresar);
        etComentario = findViewById(R.id.etComentario);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        repository = new RutaRepository();
        containerComentarios = findViewById(R.id.containerComentarios);
        btnVerMas = findViewById(R.id.btnVerMas);
        ComentariosRecientes = findViewById(R.id.ComentariosRecientes);
        ratingBar = findViewById(R.id.ratingBar);
        tvValorRating = findViewById(R.id.tvValorRating);
        ComentarButton = findViewById(R.id.ComentarButton);
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            tvValorRating.setText(String.valueOf(rating));
        });

    }

    private void asignardetalles(){
        tvMotoristaName.setText(motorista.getUsuario().getNombre());
        FileUtils.MostrarImagen(detail_detalles_motorista.this, profile_image, motorista.getUsuario().getFotoPerfil());
        tvRate.setText(motorista.getCalificacionPromedio().toString());
        repository.getComentariosPorMotorista(motorista.getIdMotorista(), new RutaRepository.RepositoryCallback<List<Comentario>>() {
            @Override
            public void onSuccess(List<Comentario> data) {

                int cont=0;
                coments=data;
                for (Comentario comentario: data) {
                    if(cont<2){
                        addComentarios(comentario);
                    }
                    cont++;
                    tvDiasService.setText(String.valueOf(cont));
                    btnVerMas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(detail_detalles_motorista.this,error,Toast.LENGTH_LONG).show();
            }
        });



        //tvDiasService.setText(""+motorista.getIdOrganizacion().toString());
        tvTelefono.setText(motorista.getUsuario().getTelefono());
        tvEmail.setText(motorista.getUsuario().getCorreo());


    }

    private void addComentarios(Comentario comentario){
        View itemView = LayoutInflater.from(detail_detalles_motorista.this).inflate(R.layout.item_comentario, containerComentarios, false);
        TextView comment_user_name = itemView.findViewById(R.id.comment_user_name);
        TextView comment_text = itemView.findViewById(R.id.comment_text);
        TextView comment_date = itemView.findViewById(R.id.comment_date);
        TextView comment_rating = itemView.findViewById(R.id.comment_rating);
        ImageView comment_user_image = itemView.findViewById(R.id.comment_user_image);

        FileUtils.MostrarImagen(detail_detalles_motorista.this, comment_user_image, comentario.getEstudiante().getUsuario().getFotoPerfil());

        comment_user_name.setText(comentario.getEstudiante().getUsuario().getNombre());
        comment_text.setText(comentario.getContenido());
        comment_date.setText(comentario.getFechaComentario());
        comment_rating.setText(String.valueOf(comentario.getPuntuacion()));
        containerComentarios.addView(itemView);
    }

    public void CargarMás(View view) {
        containerComentarios.removeAllViews();
        if(btnVerMas.getText().toString().equals("Ver más")){

            ComentariosRecientes.setText("Comentarios");
            for (Comentario comentario: coments) {
                addComentarios(comentario);
            }
            btnVerMas.setText("Ver menos");
        }else{
            ComentariosRecientes.setText("Comentarios Recientes");
            int count1=0;
            for (Comentario comentario: coments) {
                if(count1<2){
                    addComentarios(comentario);
                }
                count1++;
            }
            btnVerMas.setText("Ver más");
        }


    }

    public void Comentar(View view) {
        if(ComentarButton.getText().toString().equals("Enviar comentario")){
            if(ratingBar.getRating()==0){
                Toast.makeText(detail_detalles_motorista.this, "Ingresa al menos 0.5 estrellas", Toast.LENGTH_SHORT).show();
            }else{
                Comentario comentario = new Comentario();
                comentario.setPuntuacion(BigDecimal.valueOf(Double.parseDouble(tvValorRating.getText().toString())));
                LocalDateTime fechaCompleta = LocalDateTime.now();
                comentario.setFechaComentario(fechaCompleta.toString());
                comentario.setContenido(etComentario.getText().toString());
                comentario.setIdEstudiante(authRepository.getCurrentEstudianteID());
                comentario.setIdRuta(idRuta);

                repository.createComentario(comentario, new RutaRepository.RepositoryCallback<List<Comentario>>() {
                    @Override
                    public void onSuccess(List<Comentario> data) {
                        Toast.makeText(detail_detalles_motorista.this, "Se Ah añadido tu comentario", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

        }
    }
}