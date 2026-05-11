package com.svc.ventas.message.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresentacionUpdateRequest {

  private Long idPresentacion;

  private String presentacion;

  private BigDecimal equivalencia;

  private BigDecimal precioSugerido;

  private BigDecimal precioVenta;

  private BigDecimal precioDescuento;

  private Boolean estado;

}
