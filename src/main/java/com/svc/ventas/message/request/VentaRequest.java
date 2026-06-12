package com.svc.ventas.message.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequest {

  private String fecha;

  @NotNull(message = "El cliente es obligatorio")
  @Min(value = 1, message = "El cliente es inválido")
  private Integer idCliente;

  @NotNull(message = "El tipo de documento es obligatorio")
  @Min(value = 1, message = "El tipo de documento es inválido")
  private Long idTipoDocumento;

  private Boolean aplicarImpuesto;

  @NotNull(message = "El tipo de pago es obligatorio")
  private String tipoPago;

  @NotEmpty(message = "Debe agregar al menos un producto")
  private List<ProductoParaVender> productos;

  @NotNull(message = "El IGV es obligatorio")
  @DecimalMin(value = "0.00", inclusive = true,
          message = "El IGV no puede ser negativo")
  private BigDecimal igv;

  @NotNull(message = "El subtotal es obligatorio")
  @DecimalMin(value = "0.00", inclusive = true,
          message = "El subtotal no puede ser negativo")
  private BigDecimal subTotal;

  @NotNull(message = "El total es obligatorio")
  @DecimalMin(value = "0.00", inclusive = true,
          message = "El total no puede ser negativo")
  private BigDecimal total;

}
