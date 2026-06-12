package com.svc.ventas.message.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoCreateRequest {

  @NotNull(message = "La persona es obligatoria")
  @Min(value = 1, message = "La persona es inválida")
  private Integer idPersona;

  @NotNull(message = "La sucursal es obligatoria")
  @Min(value = 1, message = "La sucursal es inválida")
  private Long idSucursal;

}
