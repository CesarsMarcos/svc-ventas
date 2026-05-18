package com.svc.ventas.message.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteSaveResponse {

  private Integer idCliente;

  private String numDocumento;

  private String nombreCompleto;

}
