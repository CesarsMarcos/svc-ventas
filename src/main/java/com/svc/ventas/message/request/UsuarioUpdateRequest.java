package com.svc.ventas.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateRequest {

  @NotNull
  private Integer idUsuario;

  @NotNull
  private Integer idSucursal;

  @NotEmpty
  private List<Integer> roles;

  @NotBlank
  private String usuario;

  private Boolean indEstado;

}
