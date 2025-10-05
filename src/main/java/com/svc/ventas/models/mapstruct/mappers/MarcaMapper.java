package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.MarcaGetDto;
import com.svc.ventas.models.mapstruct.dto.MarcaPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Marca;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

	@Mapping(target = "indEstado", constant = "true")
	Marca mapMarca(MarcaPostDto marcaDto);

	Marca mapMarca(MarcaGetDto marcaDto);

	MarcaPostDto mapMarcaDto(Marca marca);

	MarcaGetDto mapMarcaGetDto(Marca marca);

}
