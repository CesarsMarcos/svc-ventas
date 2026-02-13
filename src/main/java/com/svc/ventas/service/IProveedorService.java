package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.springframework.transaction.annotation.Transactional;

public interface IProveedorService {

	List<ProveedorDto> proveedores();

	List<ProveedorSelectedDto> proveedoresListSelected();

	@Transactional
	Response registrar(ProveedorDto proveedor);

	@Transactional
	Response modificar(Long id, ProveedorDto proveedor);
	
	ProveedorDto obtener(Long codigo);
	
	void eliminar(Long codigo);

	Object searchProveedor(String tipoDocuento, String numDocumento);
	
}
