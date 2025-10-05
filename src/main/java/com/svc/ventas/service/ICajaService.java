package com.svc.ventas.service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CajaDTO;
import com.svc.ventas.models.mapstruct.dto.CajaDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.CajaMovimientosDTO;
import com.svc.ventas.models.mapstruct.dto.ResumenCajaDTO;

public interface ICajaService {

    CajaDetalleDTO findByFechaAndUsuario();

    Response aperturaCaja (CajaDTO cash);

    Response cerrarCaja(Long idCaja);

    Response agregarMovimiento (Long idCaja, CajaMovimientosDTO movimiento);

    ResumenCajaDTO calcularCierreCaja (Long idCaja);

}
