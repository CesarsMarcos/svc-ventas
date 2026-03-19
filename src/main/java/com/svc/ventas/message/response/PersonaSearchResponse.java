package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PersonaSearchResponse {

  private Integer idPersona;

  private String nombreCompleto;

  private String tipoDocumento;

  private String numDocumento;

  private String correo;

  private String fechaNacimiento;

  private String foto;

  private Boolean estado;

}
