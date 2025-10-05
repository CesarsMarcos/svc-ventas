package com.svc.ventas.service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CompraDto;
import com.svc.ventas.models.mapstruct.dto.CompraGetDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICompraService {

	@Transactional
	Response registrar(CompraDto compra);

	List<CompraGetDto> listado(Boolean isViewMore);

	Object details (Long id);
	
}
