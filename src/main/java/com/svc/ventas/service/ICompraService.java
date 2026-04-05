package com.svc.ventas.service;

import com.svc.ventas.message.request.CompraRequest;
import com.svc.ventas.message.response.ResponseTransaccion;
import com.svc.ventas.models.mapstruct.dto.CompraDetailDto;
import com.svc.ventas.models.mapstruct.dto.EnumDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ICompraService {

	@Transactional
	ResponseTransaccion registrar(CompraRequest compra);

	Map<String, Object> searchCompras(String ruc, String proveedor,
																		Long documentoCompra, LocalDate inicio,
																		LocalDate fin, Pageable pageable) ;

	CompraDetailDto details (Long id);

	List<EnumDto> tipoPagoCompra ();

}
