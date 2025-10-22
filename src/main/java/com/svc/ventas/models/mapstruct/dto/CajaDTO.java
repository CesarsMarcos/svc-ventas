package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CajaDTO {

    private Long idCash;

    private UsuarioDto usuario;

    @Min(0)
    @NonNull
    private BigDecimal montoApertura;

    private String estado; //abierto cerrado

    private Set<CajaMovimientosDTO> movimientos;

}
