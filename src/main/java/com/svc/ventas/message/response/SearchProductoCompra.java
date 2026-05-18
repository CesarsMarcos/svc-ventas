package com.svc.ventas.message.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductoCompra {

  private Long idProducto;

  private String nombre;

  private BigDecimal stock;

  private String imagen;

}
