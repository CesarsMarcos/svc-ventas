package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmpleadoSearchResponse {

  private Long idEmpleado;

  private String codEmpleado;

  private String tipoDocumento;

  private String numDocumento;

  private String nombreCompleto;

  private String celular;

  private String sucursal;

  private String estado;
}
