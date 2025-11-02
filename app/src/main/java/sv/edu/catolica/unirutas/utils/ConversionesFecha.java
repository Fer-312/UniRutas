package sv.edu.catolica.unirutas.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConversionesFecha {


    public static Calendar convertirStringACalendar(String fechaStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha = sdf.parse(fechaStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            return calendar;
        } catch (Exception e) {
            return null;
        }

    }

    public static Hora_Min localTimeTo_HoraMin(LocalTime hora) {
        int horas = 0;
        int min = 0;

        try {
            horas =hora.getHour() ;
            min = hora.getMinute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Hora_Min(horas, min);
    }
    public static String convertirFechaAFormatoSupabase(String fechaStr) {
        try {
            // 1️⃣ Parsear la fecha que viene como dd/MM/yyyy
            DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaStr, formatoEntrada);

            // 2️⃣ Tomar la hora local actual (del dispositivo)
            LocalTime horaActual = LocalTime.now();

            // 3️⃣ Unir fecha y hora en un LocalDateTime
            LocalDateTime fechaCompleta = LocalDateTime.of(fecha, horaActual);

            // 4️⃣ Formatear como espera Supabase para tipo timestamp
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return fechaCompleta.format(formatoSalida);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
