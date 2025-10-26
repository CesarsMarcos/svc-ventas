package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import org.springframework.transaction.annotation.Transactional;

public interface ISucursalService {

	List<SucursalDto> lista();

	@Transactional
	Response agregar(SucursalDto sucursal);

	@Transactional
	Response modificar(int id, SucursalDto sucursal);

	SucursalDto obtener(int id);

	void eliminar(int id);

}
