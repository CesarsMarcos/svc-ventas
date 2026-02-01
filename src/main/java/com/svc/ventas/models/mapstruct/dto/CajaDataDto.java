package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaDataDto {

    private String usuario;

    private BigDecimal montoApertura;

    private TotalesCaja totales;

    private BigDecimal montoCierre;

    private String horaApertura;

    private String horaCierre;

    private CajaMovimientoDetalleDTO movimiento;
}
