package com.svc.ventas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {
  FACTURA( "01", "FACTURA", true),
  BOLETA("03", "BOLETA", true),
  TICKET("03", "TICKET", true),
  NOTA_CREDITO ("07", "NOTA CREDITO", false),
  NOTA_DEBITO ("08", "NOTA CREDITO", false),
  GUIA_REMISION ("31", "GUIA REMISIÓN", false);

  private final String codigo;

  private final String label;

  private final Boolean estado;

  public static TipoDocumento from(String value) {
    return TipoDocumento.valueOf(value.replace(" ", "_").toUpperCase());
  }

  public String getValue() {
    return this.name();
  }

}

