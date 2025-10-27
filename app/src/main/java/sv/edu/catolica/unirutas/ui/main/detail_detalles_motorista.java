package sv.edu.catolica.unirutas.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.zip.Inflater;

import sv.edu.catolica.unirutas.R;
import sv.edu.catolica.unirutas.data.model.Comentario;
import sv.edu.catolica.unirutas.data.model.Motorista;
import sv.edu.catolica.unirutas.data.repository.RutaRepository;

public class detail_detalles_motorista extends AppCompatActivity {
    private Motorista motorista;
    private TextView tvMotoristaName;
    private TextView tvRate;
    private TextView tvDiasService;
    private TextView tvTelefono;
    private TextView tvEmail;
    private ImageButton btnregresar;
    private RutaRepository repository;
    private LinearLayout containerComentarios;
    private Button btnVerMas;

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
        motorista = (Motorista) getIntent().getSerializableExtra("motorista");
        tvMotoristaName = findViewById(R.id.tvMotoristaName);
        tvRate = findViewById(R.id.tvRate);
        tvDiasService = findViewById(R.id.tvDiasService);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvEmail = findViewById(R.id.tvEmail);
        btnregresar = findViewById(R.id.btnregresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        repository = new RutaRepository();
        containerComentarios = findViewById(R.id.containerComentarios);
        btnVerMas = findViewById(R.id.btnVerMas);

    }

    private void asignardetalles(){
        tvMotoristaName.setText(motorista.getUsuario().getNombre());
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
        comment_user_name.setText(comentario.getEstudiante().getUsuario().getNombre());
        comment_text.setText(comentario.getContenido());
        comment_date.setText(comentario.getFechaComentario());
        comment_rating.setText(String.valueOf(comentario.getPuntuacion()));
        containerComentarios.addView(itemView);
    }

    public void CargarMás(View view) {
        containerComentarios.removeAllViews();
        if(btnVerMas.getText().toString().equals("Ver más")){
            for (Comentario comentario: coments) {
                addComentarios(comentario);
            }
            btnVerMas.setText("Ver menos");
        }else{
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
}