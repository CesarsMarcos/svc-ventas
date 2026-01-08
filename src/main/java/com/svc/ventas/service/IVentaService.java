package com.svc.ventas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.mapstruct.dto.VentaGetDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IVentaService {
	
	List<VentaGetDto> searchVenta (Boolean isViewMore);

	Map<String, Object> searchVenta(String nombre, String documentoCliente,
																	String documentoVenta, LocalDate inicio,
																	LocalDate fin, Pageable pageable);

	@Transactional
	Response registrar(VentaRequest ventaDto);
	
	List<Venta> listadoVentasPorCliente (String dni);
	
	Object details(Long id);
}
