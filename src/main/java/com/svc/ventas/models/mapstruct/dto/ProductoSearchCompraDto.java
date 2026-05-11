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
public class ProductoSearchCompraDto {

  private Long idProducto;

  private Long idPresentacion;

  private String nombreProducto;

  private String presentacion;

  private Boolean isPrincipal;

  private BigDecimal precio;

  private BigDecimal stock;

}
