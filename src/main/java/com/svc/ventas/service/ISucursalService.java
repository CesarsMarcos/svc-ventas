package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.request.SucursalRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import org.springframework.transaction.annotation.Transactional;

public interface ISucursalService {

	List<SucursalDto> lista();

	@Transactional
	Response agregar(SucursalRequest sucursal);

	@Transactional
	Response modificar(Long id, SucursalDto sucursal);

	SucursalDto obtener(Long id);

	void eliminar(Long id);

}
