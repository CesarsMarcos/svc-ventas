package com.svc.ventas.message.request;

import jakarta.validation.constraints.*;
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

  @NotNull(message = "La sucursal es obligatoria")
  @Min(value = 1, message = "La sucursal es inválida")
  private Integer idSucursal;

  @NotEmpty(message = "Debe asignar al menos un rol")
  private List<Integer> roles;

  @NotBlank(message = "El usuario es obligatorio")
  @Size(min = 4, max = 10,
          message = "El usuario debe tener entre 4 y 10 caracteres")
  @Pattern(
          regexp = "^[a-zA-Z0-9._-]+$",
          message = "El usuario solo puede contener letras, números, punto, guion y guion bajo"
  )
  private String usuario;

  @NotNull(message = "El estado es obligatorio")
  private Boolean indEstado;

}
