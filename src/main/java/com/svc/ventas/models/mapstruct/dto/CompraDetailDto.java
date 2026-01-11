package com.svc.ventas.models.mapstruct.dto;

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
public class CompraDetailDto {

  private Long id;

  private String fecha;

  private String estado;

  private String tipoPago;

  private String tipoDocumento;

  private String serieCorrelativo;

  private String proveedor;

  private List<ProductoDetalleCompraDto> productos;

  private BigDecimal igv;

  private BigDecimal subTotal;

  private BigDecimal total;

}
