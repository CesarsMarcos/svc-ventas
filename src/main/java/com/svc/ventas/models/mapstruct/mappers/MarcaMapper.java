package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.MarcaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.svc.ventas.models.entity.Marca;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

	@Mapping(target = "indEstado", constant = "true")
	Marca mapMarca(MarcaDto marcaDto);

	Marca mapMarcaDto(Marca marca);

	MarcaDto mapMarcaGetDto(Marca marca);

}
