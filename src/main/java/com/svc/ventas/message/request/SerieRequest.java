package com.svc.ventas.message.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SerieRequest {

  private Long idSucursal;

  private Long idTipoDocumento;

  private String serie;

  private Long correlativo;

}
