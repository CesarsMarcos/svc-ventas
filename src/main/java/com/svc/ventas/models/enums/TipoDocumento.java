package com.svc.ventas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {
  FACTURA,
  BOLETA,
  TICKET,
  NOTA_CREDITO,
  NOTA_DEBITO,
  GUIA_REMISION;

  public static TipoDocumento from(String value) {
    return TipoDocumento.valueOf(value);
  }

}

