package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPagoCompra;
import jakarta.validation.constraints.NotNull;
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

  private String fecha;

  private String serie;

  private String correlativo;

  @NotNull
  private TipoDocumento tipoDocumento;

  private TipoPagoCompra TipoPago;

  @NotNull
  private Long idProveedor;

  @NotNull
  private Long idSucursal;

  @NotNull
  private List<ProductoParaComprar> productos;

  @NotNull
  private BigDecimal igv;

  @NotNull
  private BigDecimal subTotal;

  @NotNull
  private BigDecimal total;

}
