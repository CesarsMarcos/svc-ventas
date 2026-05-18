package com.svc.ventas.message.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CajaAperturaRequest {

  @NotNull
  private BigDecimal montoApertura;

}
