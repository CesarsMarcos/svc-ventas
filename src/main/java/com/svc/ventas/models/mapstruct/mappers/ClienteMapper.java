package com.svc.ventas.models.mapstruct.mappers;

import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	@Mapping(target = "idCliente", ignore = true)
	Cliente mapCliente (ClienteDto clienteDto);

	ClienteDto mapClienteDto (Cliente cliente);

}
