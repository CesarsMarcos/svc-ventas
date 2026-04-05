package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleImpresionDto {

  private String logo;

  private String razonSocial;

  private String ruc;

  private String direccion;

  private String distrito;

  private String provincia;

  private String tipoDocumento;

  private String serieCorrelativo;

  private String fecha;

  private String nomCliente;

  private String numDocumento;

  private String tipoPago;

  private List<ProductoDetalleVentaDto> productos;

  private BigDecimal igv;

  private BigDecimal subTotal;

  private BigDecimal total;

  private String totalTexto;

  private String usuarioRegistro;

}
