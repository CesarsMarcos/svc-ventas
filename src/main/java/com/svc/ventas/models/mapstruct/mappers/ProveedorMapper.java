package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Proveedor;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

	@Mapping(target = "idProveedor", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Proveedor mapToProveedor (ProveedorDto proveedorDto);

	@Mapping(source = "indEstado", target = "estado")
	ProveedorDto mapToProveedorDto (Proveedor proveedor);

	@Mapping(source = "idProveedor", target = "idProveedor")
	@Mapping(source = "numDocumento", target = "ruc")
	@Mapping(source = "razonSocial", target = "razonSocial")
	ProveedorSelectedDto mapToProveedorSelected(Proveedor proveedor);
	
}
