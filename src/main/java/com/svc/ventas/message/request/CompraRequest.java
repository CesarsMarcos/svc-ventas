package com.svc.ventas.message.request;


import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.enums.TipoPagoCompra;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraRequest {

  @NotBlank(message = "La fecha es obligatoria")
  private String fecha;

  @NotBlank(message = "La serie es obligatoria")
  @Size(min = 3, max = 10,
          message = "La serie debe tener entre 3 y 10 caracteres")
  @Pattern(
          regexp = "^[A-Za-z0-9]+$",
          message = "La serie solo puede contener letras y números"
  )
  private String serie;

  @NotNull(message = "El correlativo es obligatorio")
  @Min(value = 1, message = "El correlativo debe ser mayor a cero")
  private Long correlativo;

  @NotNull(message = "El tipo de documento es obligatorio")
  @Min(value = 1, message = "El tipo de documento es inválido")
  private Long idTipoDocumento;

  @NotNull(message = "El tipo de pago es obligatorio")
  private TipoPagoCompra TipoPago;

  @NotNull(message = "El proveedor es obligatorio")
  @Min(value = 1, message = "El proveedor es inválido")
  private Long idProveedor;

  @NotEmpty(message = "Debe agregar al menos un producto")
  private List<ProductoParaComprar> productos;

  @NotNull(message = "El IGV es obligatorio")
  @DecimalMin(value = "0.00", message = "El IGV no puede ser negativo")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal igv;

  @NotNull(message = "El subtotal es obligatorio")
  @DecimalMin(value = "0.00", message = "El subtotal no puede ser negativo")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal subTotal;

  @NotNull(message = "El total es obligatorio")
  @DecimalMin(value = "0.00", message = "El total no puede ser negativo")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal total;

}
