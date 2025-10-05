package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.ClientePostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.dto.ClienteGetDto;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	Cliente mapCliente (ClientePostDto clienteDto);

	Cliente mapCliente (ClienteGetDto clienteDto);
	
	ClienteGetDto mapClienteDto (Cliente cliente);

}
