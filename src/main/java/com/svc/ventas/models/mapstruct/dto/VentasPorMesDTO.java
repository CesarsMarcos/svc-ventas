package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentasPorMesDTO implements Serializable {

  private Integer  anio;

  private Integer  mes;

  private BigDecimal totalVentas;

}
