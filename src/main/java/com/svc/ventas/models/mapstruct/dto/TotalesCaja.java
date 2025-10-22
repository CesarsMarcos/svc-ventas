package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TotalesCaja {

    private final BigDecimal totalIngresos;

    private final BigDecimal totalEgresos;
}