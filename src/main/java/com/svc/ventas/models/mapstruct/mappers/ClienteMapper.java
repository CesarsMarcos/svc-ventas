package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.mapstruct.dto.ClienteGetDto;
import com.svc.ventas.models.mapstruct.dto.ClienteSelectedDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	@Mapping(target = "persona", source = "persona")
	@Mapping(target = "indEstado", constant = "true")
	Cliente mapCliente (ClienteCreateRequest clienteRequest,
											Persona persona);

	@Mapping(target = "idCliente", source = "idCliente")
	@Mapping(target = "nombreCompleto", source = "cliente.persona.nombreMostrado")
	ClienteSelectedDto mapToClienteSelected(Cliente cliente);

	ClienteDto mapClienteDto (Cliente cliente);

	@Mapping(source = "cliente.persona.nombreMostrado", target = "nombreCompleto")
	@Mapping(source = "persona.numDocumento", target="numDocumento")
	@Mapping(source = "persona.telefono", target="telefono")
	@Mapping(source = "persona.tipoDocumento", target="tipoDocumento")
	ClienteGetDto mapClienteGet (Cliente cliente);

}
