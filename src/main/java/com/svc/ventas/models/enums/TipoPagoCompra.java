package com.svc.ventas.models.enums;

public enum TipoPagoCompra {
  CONTADO ("CONTADO"),
  CREDITO ("CREDITO"),
  TRANSFERENCIA ("TRANSFERENCIA");

  private final String label;

  TipoPagoCompra(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return this.name();
  }

  public static TipoPagoCompra from(String value) {
    return TipoPagoCompra.valueOf(value.toUpperCase());
  }
}
