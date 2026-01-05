package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.SucursalRequest;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Sucursal;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

	SucursalDto mapToSucursalDTO(Sucursal sucursal);

	@Mapping(target = "empresa", source = "empresa")
	@Mapping(target = "direccion", source = "sucursal.direccion")
	@Mapping(target = "email", source = "sucursal.email")
	@Mapping(target = "razonSocial", source = "sucursal.razonSocial")
	@Mapping(target = "telefono", source = "sucursal.telefono")
	@Mapping(target = "indEstado", constant = "true")
	Sucursal mapRequestToSucursalPost(SucursalRequest sucursal, Empresa empresa);

	Sucursal mapToSucursalPost(SucursalDto sucursalDto);

}
