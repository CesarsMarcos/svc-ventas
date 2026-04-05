package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaDataDto {

    private String usuario;

    private BigDecimal montoApertura;

    private TotalesCaja totales;

    private BigDecimal montoCierre;

    private LocalDateTime horaApertura;

    private LocalDateTime horaCierre;

    private CajaMovimientoDetalleDTO movimiento;
}
