package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.SucursalGetDto;
import com.svc.ventas.models.mapstruct.dto.SucursalPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface ISucursalService {

	List<SucursalGetDto> lista();

	@Transactional
	Response agregar(SucursalPostDto sucursal);

	@Transactional
	Response modificar(int id, SucursalPostDto sucursal);

	SucursalGetDto obtener(int id);

	void eliminar(int id);

}
