package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresentacionesCompraDto {

  private Long idPresentacion;

  private String presentacion;

  private BigDecimal equivalencia;

  private BigDecimal precio;

}
