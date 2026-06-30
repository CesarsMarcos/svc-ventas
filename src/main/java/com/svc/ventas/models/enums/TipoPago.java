package com.svc.ventas.models.enums;

import lombok.Getter;

@Getter
public enum TipoPago {
  EFECTIVO("EFECTIVO"),
  //TARJETA("TARJETA"),
  //TRANSFERENCIA("TRANSFERENCIA"),
  YAPE("YAPE"),
  PLIN("PLIN"),
  MIXTO("MIXTO");

  private final String label;

  TipoPago(String label) {
    this.label = label;
  }

  public String getValue() {
    return this.name();
  }

  public static TipoPago from(String value) {
    return TipoPago.valueOf(value.toUpperCase());
  }

}

