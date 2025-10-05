package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.TipoDocumentoGetDto;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoSelectedDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper {

	@Mapping(target = "indEstado", constant = "true")
	TipoDocumento mapTipoDocumento(TipoDocumentoDto tipoDto);

	TipoDocumentoGetDto mapToDto (TipoDocumento tipoDocumento);
	
	TipoDocumentoSelectedDto mapToTipoDocumentoSelected (TipoDocumento tipo);
	
}
