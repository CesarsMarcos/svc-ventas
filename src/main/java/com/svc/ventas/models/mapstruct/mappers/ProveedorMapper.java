package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.ProveedorGetDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorPostDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Proveedor;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

	@Mapping(target = "indEstado", constant = "true")
	Proveedor mapToProveedor (ProveedorPostDto proveedorDto);
	
	ProveedorGetDto mapToProveedorDto (Proveedor proveedor);

	@Mapping(source = "idProveedor", target = "idProveedor")
	@Mapping(source = "numDocumento", target = "ruc")
	@Mapping(source = "razonSocial", target = "razonSocial")
	ProveedorSelectedDto mapToProveedorSelected(Proveedor proveedor);
	
}
