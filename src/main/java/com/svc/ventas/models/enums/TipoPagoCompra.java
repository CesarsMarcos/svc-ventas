package com.svc.ventas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPagoCompra {
  CONTADO ("CONTADO", true),
  CREDITO ("CREDITO", false),
  TRANSFERENCIA ("TRANSFERENCIA", false);

  private final String label;

  private final Boolean estado;

  public String getValue() {
    return this.name();
  }

  public static TipoPagoCompra from(String value) {
    return TipoPagoCompra.valueOf(value.toUpperCase());
  }
}
