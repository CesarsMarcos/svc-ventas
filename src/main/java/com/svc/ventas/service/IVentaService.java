package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.mapstruct.dto.VentaDto;
import com.svc.ventas.models.mapstruct.dto.VentaGetDto;
import org.springframework.transaction.annotation.Transactional;

public interface IVentaService {
	
	List<VentaGetDto> listado (Boolean isViewMore);

	@Transactional
	Response registrar(VentaDto ventaDto);
	
	List<Venta> listadoVentasPorCliente (String dni);
	
	Object details(Long id);
}
