package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.entity.TipoDocumento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UltimasVentasDTO {

  private Long idVenta;

  private String cliente;

  private String comprobante;

  private LocalDateTime fecha;

  private BigDecimal total;

}
