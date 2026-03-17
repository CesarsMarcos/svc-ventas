package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoDocumento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UltimasVentasDTO {

  private Long idVenta;

  private String cliente;

  private TipoDocumento comprobante;

  private LocalDate fecha;

  private BigDecimal total;

}
