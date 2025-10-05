package com.svc.ventas.models.mapstruct.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaDetalleDTO {

    private Long IdCaja;

    private String usuario;

    private BigDecimal montoApertura;

    private BigDecimal montoCierre;

    private String horaApertura;

    private String horaCierre;

    private String estado;

    private CajaMovimientoDetalleDTO movimiento;

}
