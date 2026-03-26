package com.svc.ventas.message.response;

import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.enums.TipoPagoCompra;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompraResponse {

  private Long id;

  private String fecha;

  private String estado;

  @NotNull
  private String tipoDocumento;

  private TipoPagoCompra tipoPago;

  @NotNull
  private String proveedor;

  @NotNull
  private BigDecimal igv;

  @NotNull
  private BigDecimal subTotal;

  @NotNull
  private BigDecimal total;

}
