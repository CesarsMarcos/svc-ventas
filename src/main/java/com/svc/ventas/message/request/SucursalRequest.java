package com.svc.ventas.message.request;

import jakarta.validation.constraints.Email;
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
public class SucursalRequest {

  @NotNull
  private Integer idEmpresa;

  private String numDocumento;

  @NotBlank
  private String razonSocial;

  @NotBlank
  private String representante;

  @NotBlank
  private String direccion;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String telefono;

}
