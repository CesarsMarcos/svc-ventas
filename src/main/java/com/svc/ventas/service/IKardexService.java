package com.svc.ventas.service;

import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.KardexResumenDTO;

import java.time.LocalDate;
import java.util.List;

public interface IKardexService {

  List<KardexDetalleDTO> obtenerKardexPorProducto(Long idProducto);

  List<KardexResumenDTO> listarKardexPorFecha(LocalDate fechaInicio, LocalDate fechaFin);

}
