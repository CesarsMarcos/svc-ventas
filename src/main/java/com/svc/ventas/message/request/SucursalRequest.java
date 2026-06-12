package com.svc.ventas.message.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {

  @Pattern(
          regexp = "^\\d{11}$",
          message = "El RUC debe tener 11 dígitos"
  )
  private String numDocumento;

  @NotBlank(message = "La razón social es obligatoria")
  @Size(min = 3, max = 100, message = "La razón social debe tener entre 3 y 100 caracteres")
  private String razonSocial;

  @NotBlank(message = "El representante es obligatorio")
  @Size(min = 3, max = 100, message = "El representante debe tener entre 3 y 100 caracteres")
  private String representante;

  @NotBlank(message = "La dirección es obligatoria")
  @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
  private String direccion;

  @NotBlank(message = "El correo electrónico es obligatorio")
  @Email(message = "El correo electrónico no tiene un formato válido")
  @Size(max = 100, message = "El correo electrónico no puede exceder 100 caracteres")
  private String email;

  @NotBlank(message = "El teléfono es obligatorio")
  @Pattern(
          regexp = "^[0-9+\\-\\s]{7,20}$",
          message = "El teléfono tiene un formato inválido"
  )
  private String telefono;

}
