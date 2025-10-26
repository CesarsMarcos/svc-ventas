package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KardexResumenDTO {
  private Long idProducto;
  private String codigo;
  private String nombre;
  private BigDecimal saldoInicial;
  private BigDecimal entradas;
  private BigDecimal salidas;
  private BigDecimal saldoFinal;
}

