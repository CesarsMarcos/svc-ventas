package com.svc.ventas.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public final class AppUtils {

    public AppUtils() {
    }

    public static LocalDate convert(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
    public static String obtenerFechaActual() {
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatoFecha);
    }

    public static String obtenerHoraActual() {
        LocalTime hora = LocalTime.now();
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        return hora.format(formatoHora);
    }

}
