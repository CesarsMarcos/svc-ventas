package com.svc.ventas.message.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorRequest {

  @NotBlank(message = "El tipo de documento es obligatorio")
  @Size(max = 20, message = "El tipo de documento no puede exceder 20 caracteres")
  private String tipoDocumento;

  @NotBlank(message = "El número de documento es obligatorio")
  @Size(min = 8, max = 20, message = "El número de documento debe tener entre 8 y 20 caracteres")
  private String numDocumento;

  @NotBlank(message = "La razón social es obligatoria")
  @Size(min = 3, max = 200, message = "La razón social debe tener entre 3 y 200 caracteres")
  private String razonSocial;

  @Size(max = 250, message = "La dirección no puede exceder 250 caracteres")
  private String direccion;

  private String telefono;

  @Email(message = "El correo electrónico no es válido")
  @Size(max = 50, message = "El correo no puede exceder 50 caracteres")
  private String correo;

  @Size(max = 150, message = "El representante no puede exceder 150 caracteres")
  private String representante;

  private String telefonoContacto;

  @Size(max = 100, message = "El nombre del banco no puede exceder 100 caracteres")
  private String banco;

  @Size(max = 50, message = "El número de cuenta no puede exceder 50 caracteres")
  private String numCuenta;

}
