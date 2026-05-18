package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.ProveedorRequest;
import com.svc.ventas.message.response.ProveedorSaveResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.message.response.ResponseData;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IProveedorService {

	Map<String, Object> searchProveedor(String razonSocial, Pageable pageable);

	List<ProveedorDto> proveedores();

	List<ProveedorSelectedDto> proveedoresListSelected();

	@Transactional
	ResponseData<ProveedorSaveResponse> registrar(ProveedorRequest proveedor);

	@Transactional
	Response modificar(Long id, ProveedorDto proveedor);
	
	ProveedorDto obtener(Long codigo);
	
	void eliminar(Long codigo);

	Object searchProveedor(String tipoDocuento, String numDocumento);
	
}
