package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.message.request.ProductoParaComprar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraDto {

  private String fecha;

  private String serie;

  private String correlativo;

  @NotNull
  private TipoDocumentoDto tipoDocumento;

  @NotBlank
  private String tipoPago;

  @NotNull
  private ProveedorGetDto proveedor;

  @NotNull
  private List<ProductoParaComprar> productos;

  @NotNull
  private BigDecimal igv;

  @NotNull
  private BigDecimal subTotal;

  @NotNull
  private BigDecimal total;

}
