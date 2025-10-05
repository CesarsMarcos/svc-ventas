package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorGetCompraDto {

  private String numDocumento;

  private String razonSocial;

  private String direccion;

  private String correo;

}
