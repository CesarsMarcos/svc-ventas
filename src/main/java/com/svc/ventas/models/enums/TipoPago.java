package com.svc.ventas.models.enums;

public enum TipoPago {
  EFECTIVO("EFECTIVO"),
  TARJETA("TARJETA"),
  TRANSFERENCIA("TRANSFERENCIA"),
  YAPE("YAPE"),
  PLIN("PLIN"),
  MIXTO("MIXTO");

  private final String label;

  TipoPago(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return this.name();
  }

  public static TipoPago from(String value) {
    return TipoPago.valueOf(value.toUpperCase());
  }

}

