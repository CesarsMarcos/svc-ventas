package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.validation.constraints.NotBlank;
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

  @NotNull(message = "El tipo de documento es obligatorio")
  private TipoDocumentoPersona tipoDocumento;

  @NotBlank(message = "El número de documento es obligatorio")
  private String numDocumento;

  private String razonSocial;

  @NotBlank(message = "El nombre obligatoria")
  private String nombre;

  @NotBlank(message = "El apellido materno obligatoria")
  private String apeMaterno;

  @NotBlank(message = "El apellido paterno obligatoria")
  private String apePaterno;

}
