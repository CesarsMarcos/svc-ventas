package com.svc.ventas.message.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorSaveResponse {

  private Long idProveedor;

  private String numDocumento;

  private String razonSocial;

}
