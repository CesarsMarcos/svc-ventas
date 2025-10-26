package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaMovimientosDTO {

    private Long idCajaMovimiento;

    @NotBlank
    private TipoMovimiento tipoMovimiento;

    private String documento;

    private TipoPago tipoPago;

    @Min(0)
    private BigDecimal monto;

    private String descripcion;

}
