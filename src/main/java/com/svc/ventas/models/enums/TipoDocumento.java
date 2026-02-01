package com.svc.ventas.models.enums;

public enum TipoDocumento {
  FACTURA("FACTURA"),
  BOLETA("BOLETA"),
  TICKET("TICKET"),
  NOTA_CREDITO ("NOTA CREDITO"),
  GUIA_REMISION ("GUIA REMISIÓN");

  private final String label;

  public static TipoDocumento from(String value) {
    return TipoDocumento.valueOf(value.replace(" ", "_").toUpperCase());
  }

  TipoDocumento(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return this.name();
  }

}

