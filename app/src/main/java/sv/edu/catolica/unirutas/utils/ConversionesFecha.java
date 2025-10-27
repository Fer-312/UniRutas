package sv.edu.catolica.unirutas.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
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


}
