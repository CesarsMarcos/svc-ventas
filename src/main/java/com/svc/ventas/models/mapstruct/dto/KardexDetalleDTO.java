package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KardexDetalleDTO {

  private LocalDate fechaMovimiento;

  private String tipoMovimiento;

  private String documento;

  private BigDecimal cantidadEntrada;

  private BigDecimal cantidadSalida;

  private BigDecimal saldo;

  private BigDecimal costoUnitario;

  private BigDecimal totalMovimiento;

}
