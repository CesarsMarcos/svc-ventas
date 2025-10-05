package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.SucursalGetDto;
import com.svc.ventas.models.mapstruct.dto.SucursalPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Sucursal;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

	SucursalGetDto mapToSucursalDTO(Sucursal sucursal);

	@Mapping(target = "indEstado", constant = "true")
	Sucursal mapToSucursalPost(SucursalPostDto sucursalDto);

	Sucursal mapToSucursalGet(SucursalGetDto sucursalDto);

}
