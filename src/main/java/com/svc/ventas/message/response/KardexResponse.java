package com.svc.ventas.message.response;

import java.time.LocalDate;

public record KardexResponse(

        Long idProducto,

        String nombre,

        String codigo,

        String usuario,

        LocalDate fechaMovimiento,

        String tipoMovimiento,

        String documento,

        String tipoPago,

        Integer stockAnterior,

        Integer ingreso,

        Integer egreso,

        Integer stockActual
) {}
