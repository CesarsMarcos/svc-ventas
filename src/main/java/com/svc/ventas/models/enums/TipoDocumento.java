package com.svc.ventas.models.enums;

import lombok.Getter;

@Getter
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

  public String getValue() {
    return this.name();
  }

}

