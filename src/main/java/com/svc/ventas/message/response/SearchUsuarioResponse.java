package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchUsuarioResponse {

  private Long idUsuario;

  private String nombreCompleto;

  private String tipoDocumento;

  private String numDocumento;

  private String usuario;

  private String[] roles;

  private Boolean estado;

}
