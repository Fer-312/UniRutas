package sv.edu.catolica.unirutas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import sv.edu.catolica.unirutas.entidades.Ruta;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerRutasInscritas;
    private LinearLayout containerRutasFavoritas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        containerRutasInscritas = findViewById(R.id.containerRutasInscritas);
        containerRutasFavoritas = findViewById(R.id.containerRutasFavoritas);

        // Agregar rutas de ejemplo
        agregarRutasFavoritas();
        agregarRutasInscritas();

    }
    private void agregarRutasFavoritas() {
        List<Ruta> rutasFavoritas = Arrays.asList(
                new Ruta("UNICAES", "San Salvador", "7:30 P.M", ""),
                new Ruta("UNICAES", "Santa Ana", "5:00 P.M", ""),
                new Ruta("UNICAES", "Santa Ana", "5:00 P.M", ""),
                new Ruta("UNICAES", "Santa Ana", "5:00 P.M", ""),
                new Ruta("UNICAES", "Santa Ana", "5:00 P.M", "")
        );

        for (Ruta ruta : rutasFavoritas) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_ruta, containerRutasFavoritas, false);

            TextView tvRuta = itemView.findViewById(R.id.tvRutaOrigen);
            TextView tvHorario = itemView.findViewById(R.id.tvHorario);

            tvRuta.setText("Ruta " + ruta.getOrigen() + " ➡ " + ruta.getDestino());
            tvHorario.setText(ruta.getHorario());

            containerRutasFavoritas.addView(itemView);
        }
    }

    private void agregarRutasInscritas() {
        List<Ruta> rutasInscritas = Arrays.asList(
                new Ruta("San Salvador", "UNICAES", "Hoy · 6:45 AM", "Lleno"),
                new Ruta("San Salvador", "UNICAES", "Hoy · 8:25 AM", "Disponible"),
                new Ruta("Santa Tecla", "UNICAES", "Mañana · 7:15 AM", "Disponible"),
                new Ruta("UNICAES", "San Salvador", "Hoy · 4:30 PM", "Lleno")
        );

        for (Ruta ruta : rutasInscritas) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_ruta, containerRutasInscritas, false);

            TextView tvRutaOrigen = itemView.findViewById(R.id.tvRutaOrigen);
            TextView tvRutaDestino = itemView.findViewById(R.id.tvRutaDestino);
            TextView tvHorario = itemView.findViewById(R.id.tvHorario);
            TextView tvEstado = itemView.findViewById(R.id.tvEstado);

            tvRutaOrigen.setText("Ruta " + ruta.getOrigen());
            tvRutaDestino.setText(ruta.getDestino());
            tvHorario.setText(ruta.getHorario());
            tvEstado.setText(ruta.getEstado());

            // Cambiar color según el estado
            if ("Lleno".equals(ruta.getEstado())) {
                tvEstado.setBackgroundResource(R.drawable.bg_badge_red);
            } else {
                tvEstado.setBackgroundResource(R.drawable.bg_badge_green);
            }

            containerRutasInscritas.addView(itemView);
        }
    }


}