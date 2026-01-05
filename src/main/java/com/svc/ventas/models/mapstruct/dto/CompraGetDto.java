package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPagoCompra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraGetDto {

  private Long id;

  private String fecha;

  private String estado;

  @NotNull
  private TipoDocumento tipoDocumento;

  private TipoPagoCompra tipoPago;

  @NotNull
  private ProveedorGetCompraDto proveedor;

  @NotNull
  private List<ProductoDetalleDto> productos;

  @NotNull
  private BigDecimal igv;

  @NotNull
  private BigDecimal subTotal;

  @NotNull
  private BigDecimal total;

}
