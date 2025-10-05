package com.svc.ventas.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumeroATexto {

    private static final String[] UNIDADES = {
            "", "UNO", "DOS", "TRES", "CUATRO", "CINCO",
            "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ",
            "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE",
            "DIECISÉIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE"
    };

    private static final String[] DECENAS = {
            "", "", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
            "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private static final String[] CENTENAS = {
            "", "CIEN", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
            "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS",
            "OCHOCIENTOS", "NOVECIENTOS"
    };

    /**
     * Convierte un número en BigDecimal a letras con centavos y moneda.
     * @param monto BigDecimal con 2 decimales.
     * @param moneda Nombre de la moneda (ej: "SOLES", "DÓLARES")
     * @return Texto en formato facturación.
     */
    public static String convertir(BigDecimal monto, String moneda) {
        // Redondeamos a 2 decimales
        monto = monto.setScale(2, RoundingMode.HALF_UP);

        // Parte entera y decimal
        int parteEntera = monto.intValue();
        int centavos = monto.remainder(BigDecimal.ONE)
                .movePointRight(2)
                .intValue();

        return convertirNumero(parteEntera)
                + " CON " + String.format("%02d", centavos) + "/100 "
                + moneda;
    }

    private static String convertirNumero(int numero) {
        if (numero == 0) {
            return "CERO";
        }
        if (numero < 20) {
            return UNIDADES[numero];
        }
        if (numero < 100) {
            return DECENAS[numero / 10]
                    + ((numero % 10 != 0) ? " Y " + UNIDADES[numero % 10] : "");
        }
        if (numero < 1000) {
            if (numero == 100) return "CIEN";
            return CENTENAS[numero / 100]
                    + ((numero % 100 != 0) ? " " + convertirNumero(numero % 100) : "");
        }
        if (numero < 1000000) {
            int miles = numero / 1000;
            int resto = numero % 1000;
            String textoMiles = (miles == 1) ? "MIL" : convertirNumero(miles) + " MIL";
            return textoMiles + ((resto != 0) ? " " + convertirNumero(resto) : "");
        }
        if (numero < 1000000000) {
            int millones = numero / 1000000;
            int resto = numero % 1000000;
            String textoMillones = (millones == 1) ? "UN MILLÓN" : convertirNumero(millones) + " MILLONES";
            return textoMillones + ((resto != 0) ? " " + convertirNumero(resto) : "");
        }
        return "NÚMERO DEMASIADO GRANDE";
    }

}