package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.svc.ventas.models.entity.Persona;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

	@Mapping(target = "idPersona", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Persona mapToPersona (PersonaDto personaDto);


	PersonaDto mapToPersonaDto (Persona persona);

	PersonaDto map (Persona persona);

}
