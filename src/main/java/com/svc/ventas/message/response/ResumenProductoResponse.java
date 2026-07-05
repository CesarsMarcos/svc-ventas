package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumenProductoResponse {

  private Long cantidadProductos;

  private BigDecimal valorInventario;

  private Long stockCritico;

  private Long categorias;

}
