package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.PersonaGetDto;
import com.svc.ventas.models.mapstruct.dto.PersonaPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Persona;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

	@Mapping(target = "indEstado", constant = "true")
	Persona mapToPersona (PersonaPostDto personaDto);

	Persona mapToPersona (PersonaGetDto personaDto);

	PersonaPostDto mapToPersonaDto (Persona persona);

	PersonaGetDto map (Persona persona);

}
