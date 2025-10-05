package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadMedidaGetDto {

    private long idUmedida;

    private String nombre;

    private String prefijo;

}
