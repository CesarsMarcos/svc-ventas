package com.svc.ventas.models.mapstruct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
public class VariacionVentasDTO implements Serializable {

  private BigDecimal venta;

  private BigDecimal variacion;

  public VariacionVentasDTO(BigDecimal venta, BigDecimal ventaPasada) {
    this.venta = (venta != null) ? venta : BigDecimal.ZERO;
    this.variacion = calcularVariacion(venta, ventaPasada);
  }

  public BigDecimal getVenta() {
    return venta;
  }

  public BigDecimal getVariacion() {
    return variacion;
  }

  private BigDecimal calcularVariacion(BigDecimal actual, BigDecimal pasada) {
    if (pasada == null || pasada.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    return actual.subtract(pasada)
            .divide(pasada, 2, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100));
  }

}
