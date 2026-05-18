package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreateParaVentaRequest {

  private TipoDocumentoPersona tipoDocumento;

  private String numDocumento;

  private String razonSocial;

  private String nombre;

  private String apeMaterno;

  private String apePaterno;

}
