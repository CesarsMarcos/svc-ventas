package com.svc.ventas.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacionRequest {

  @NotBlank(message = "El usuario es obligatorio")
  @Size(min = 4, max = 10,
          message = "El usuario debe tener entre 4 y 10 caracteres")
  @Pattern(
          regexp = "^[a-zA-Z0-9._-]+$",
          message = "El usuario solo puede contener letras, números, punto, guion y guion bajo"
  )
  private String usuario;

  @NotBlank(message = "La clave es obligatoria")
  @Size(min = 8, max = 15,
          message = "La clave debe tener entre 8 y 15 caracteres")
  private String clave;
}
