package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.PersonaSearchResponse;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.models.mapstruct.dto.PersonaEmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.PersonaListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.svc.ventas.models.entity.Persona;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

	@Mapping(target = "idPersona", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Persona mapToPersona (PersonaDto personaDto);

	PersonaDto map (Persona persona);

	@Mapping(target = "id", source = "idPersona")
	@Mapping(target = "nombreCompleto", expression = "java(getNombreCompleto(persona))")
	PersonaEmpleadoDto mapToPersonaEmpleado(Persona persona);

	PersonaListDto mapToPersonaListDto(Persona persona);

	@Mapping(target = "idPersona", source = "idPersona")
	@Mapping(target = "nombreCompleto", expression = "java(persona.getNombre().concat(\" \").concat(persona.getApePaterno()))")
	@Mapping(target = "tipoDocumento", source = "tipoDocumento")
	@Mapping(target = "numDocumento", source = "numDocumento")
	@Mapping(target = "correo", source = "correo")
	@Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
	@Mapping(target = "foto", source = "foto")
	@Mapping(target = "estado", source = "indEstado")
	PersonaSearchResponse mapToResponseSearch(Persona persona);


	default String getNombreCompleto(Persona persona) {
		return persona.getNombre().concat(" ".concat(persona.getApePaterno()));
	}

}
