package com.svc.ventas.models.mapstruct.mappers;

import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.TipoPersona;
import com.svc.ventas.models.mapstruct.dto.TipoPersonaDto;

@Mapper(componentModel = "spring")
public interface TipoPersonaMapper {

	TipoPersona mapTipoPersona(TipoPersonaDto tipoDto);
	
	TipoPersonaDto mapTipoPersonaDto(TipoPersona tipo);

}
