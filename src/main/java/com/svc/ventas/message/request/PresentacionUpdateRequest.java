package com.svc.ventas.message.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresentacionUpdateRequest {

  private Long idPresentacion;

  @NotBlank(message = "La presentación es obligatoria")
  @Size(min = 2, max = 100,
          message = "La presentación debe tener entre 2 y 100 caracteres")
  private String presentacion;

  @NotNull(message = "La equivalencia es obligatoria")
  @DecimalMin(value = "0.01",
          message = "La equivalencia debe ser mayor a cero")
  private BigDecimal equivalencia;

  @NotNull(message = "El precio sugerido es obligatorio")
  @DecimalMin(value = "0.00",
          message = "El precio sugerido no puede ser negativo")
  private BigDecimal precioSugerido;

  @NotNull(message = "El precio de venta es obligatorio")
  @DecimalMin(value = "0.00",
          message = "El precio de venta no puede ser negativo")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal precioVenta;

  @NotNull(message = "El precio de descuento es obligatorio")
  @DecimalMin(value = "0.00",
          message = "El precio de descuento no puede ser negativo")
  private BigDecimal precioDescuento;

  @NotNull(message = "El estado es obligatorio")
  private Boolean estado;

}
