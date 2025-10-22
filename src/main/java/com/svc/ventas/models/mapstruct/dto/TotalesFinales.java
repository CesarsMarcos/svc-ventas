package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public  class TotalesFinales {

    private final BigDecimal totalEfectivoEnCaja;

    private final BigDecimal totalCtaBancaria;

    private final BigDecimal totalCuadre;

}
