package com.svc.ventas.message.request;

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

  @NotNull
  private Integer idPersona;

  @NotNull
  private Integer idSucursal;

}
