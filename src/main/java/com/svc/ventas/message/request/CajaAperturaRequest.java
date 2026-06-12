package com.svc.ventas.message.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CajaAperturaRequest {

  @NotNull(message = "El monto de apertura es obligatorio")
  @DecimalMin(value = "0.00", message = "El monto de apertura no puede ser negativo")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal montoApertura;

}
