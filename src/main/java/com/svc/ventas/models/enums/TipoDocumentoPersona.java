package com.svc.ventas.models.enums;

public enum TipoDocumentoPersona {
  DNI("DNI"),
  RUC("RUC"),
  PASAPORTE("PASAPORTE"),
  CARNET_EXTRANJERIA("CARNET EXTRANJERIA");

  private final String label;

  public static TipoDocumentoPersona from(String value) {
    return TipoDocumentoPersona.valueOf(value.replace(" ", "_").toUpperCase());
  }

  TipoDocumentoPersona(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return this.name();
  }
}
