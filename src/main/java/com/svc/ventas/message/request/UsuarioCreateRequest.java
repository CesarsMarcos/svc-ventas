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
public class UsuarioCreateRequest {

  private Integer id;

  @NotEmpty(message = "Debe asignar al menos un rol")
  private List<Integer> roles;

  private Long idSucursal;

  @NotBlank(message = "El usuario es obligatorio")
  @Size(min = 4, max = 10, message = "El usuario debe tener entre 4 y 10 caracteres")
  @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
          message = "El usuario solo puede contener letras, números, punto, guion y guion bajo"
  )
  private String usuario;

  @NotBlank(message = "La clave es obligatoria")
  @Size(min = 8, max = 15, message = "La clave debe tener entre 8 y 15 caracteres")
  private String clave;
}
