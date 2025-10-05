package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteGetVentaDto implements Serializable {

  private String numDocumento;

  private String nombreCompleto;

  private String correo;

  private String direccion;

}
