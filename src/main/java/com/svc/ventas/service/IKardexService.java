package com.svc.ventas.service;

import com.svc.ventas.message.response.KardexResponse;
import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IKardexService {

  List<KardexDetalleDTO> obtenerKardexPorProducto(Long idProducto);

  List<KardexResponse> listarKardexPorFecha(Long idSucursal, Long idProducto, String fechaInicio, String fechaFin);

}
