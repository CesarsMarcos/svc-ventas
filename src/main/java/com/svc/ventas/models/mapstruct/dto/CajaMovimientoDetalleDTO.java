package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaMovimientoDetalleDTO {

    private List<CajaMovimientosDTO> ingresos;

    private List<CajaMovimientosDTO> devoluciones;

    private List<CajaMovimientosDTO> salidas;

    private List<CajaMovimientosDTO> prestamos;

}
