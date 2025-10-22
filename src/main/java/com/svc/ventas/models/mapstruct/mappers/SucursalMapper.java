package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Sucursal;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

	SucursalDto mapToSucursalDTO(Sucursal sucursal);

	@Mapping(target = "idSucursal", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Sucursal mapToSucursalPost(SucursalDto sucursalDto);

	Sucursal mapToSucursalGet(SucursalDto sucursalDto);

}
