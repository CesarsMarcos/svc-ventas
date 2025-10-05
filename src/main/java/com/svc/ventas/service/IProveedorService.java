package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProveedorGetDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorPostDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.springframework.transaction.annotation.Transactional;

public interface IProveedorService {

	List<ProveedorGetDto> proveedores();

	List<ProveedorSelectedDto> proveedoresListSelected();

	@Transactional
	Response registrar(ProveedorPostDto proveedor);

	@Transactional
	Response modificar(Integer id, ProveedorPostDto proveedor);
	
	ProveedorGetDto obtener(int codigo);
	
	void eliminar(int codigo);
	
}
