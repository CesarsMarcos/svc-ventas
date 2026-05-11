package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockPresentacionDto {

  private Long idPresentacion;

  private Long idProductoStock;

  private String presentacion;

  private BigDecimal equivalencia;

  private BigDecimal precioSugerido;

  private BigDecimal precioVenta;

  private BigDecimal precioDescuento;

  private Boolean estado;

}
