package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.mapstruct.dto.ClienteGetDto;
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

	Cliente mapDtoToEntity(ClienteDto clienteDto);

	ClienteDto mapClienteDto (Cliente cliente);

	@Mapping(target = "nombreCompleto", expression = "java(cliente.getPersona().getNombre().concat(\" \").concat(cliente.getPersona().getApePaterno()))")
	@Mapping(source = "persona.numDocumento", target="numDocumento")
	@Mapping(source = "persona.telefono", target="telefono")
	ClienteGetDto mapClienteGet (Cliente cliente);

}
