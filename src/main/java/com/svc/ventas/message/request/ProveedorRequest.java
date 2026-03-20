package com.svc.ventas.message.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorRequest {

  private String tipoDocumento;

  private String numDocumento;

  private String razonSocial;

  private String direccion;

  private String telefono;

  private String correo;

  private String representante;

  private String telefonoContacto;

  private String banco;

  private String numCuenta;

}
