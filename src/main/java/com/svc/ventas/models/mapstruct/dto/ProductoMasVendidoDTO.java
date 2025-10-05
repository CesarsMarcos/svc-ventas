package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMasVendidoDTO implements Serializable {

  private String nombre;

  private Long cantidadVendida;

  private BigDecimal valorVentas;

  private Integer stock;

  private Boolean estado;

}
