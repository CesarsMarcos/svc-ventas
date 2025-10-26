package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.TipoDocumento;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper {

	@Mapping(target = "indEstado", constant = "true")
	TipoDocumento mapTipoDocumento(TipoDocumentoDto tipoDto);

	TipoDocumentoDto mapToDto (TipoDocumento tipoDocumento);
	
	TipoDocumentoDto mapToTipoDocumentoSelected (TipoDocumento tipo);
	
}
