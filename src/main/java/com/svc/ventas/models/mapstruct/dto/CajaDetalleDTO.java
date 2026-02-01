package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.EstadoCaja;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaDetalleDTO {

    private Long IdCaja;

    private Boolean existeCajaActiva;

    private EstadoCaja estado;

    private CajaDataDto dataCaja;



}
