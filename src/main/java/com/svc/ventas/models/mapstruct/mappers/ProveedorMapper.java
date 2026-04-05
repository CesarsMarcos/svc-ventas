package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.ProveedorRequest;
import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorSelectedDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Proveedor;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

	@Mapping(target = "idProveedor", ignore = true)
	@Mapping(source = "tipoDocumento", target = "tipoDocumento")
	@Mapping(source = "numDocumento", target = "numDocumento")
	@Mapping(source = "razonSocial", target = "razonSocial")
	@Mapping(source = "direccion", target = "direccion")
	@Mapping(source = "telefono", target = "telefono")
	@Mapping(source = "correo", target = "correo")
	@Mapping(source = "representante", target = "representante")
	@Mapping(source = "telefonoContacto", target = "telefonoContacto")
	@Mapping(source = "banco", target = "banco")
	@Mapping(source = "numCuenta", target = "nroCuenta")
	@Mapping(target = "indEstado", constant = "true")
	Proveedor mapToProveedor(ProveedorRequest request);

	@Mapping(source = "indEstado", target = "estado")
	ProveedorDto mapToProveedorDto (Proveedor proveedor);

	@Mapping(source = "idProveedor", target = "idProveedor")
	@Mapping(source = "numDocumento", target = "ruc")
	@Mapping(source = "razonSocial", target = "razonSocial")
	ProveedorSelectedDto mapToProveedorSelected(Proveedor proveedor);
	
}
