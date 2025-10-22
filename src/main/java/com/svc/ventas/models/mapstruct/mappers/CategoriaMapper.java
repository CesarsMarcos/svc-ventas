package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Categoria;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

	@Mapping(target = "idCategoria", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Categoria mapToEntity (CategoriaDto categoriaDto);

	Categoria mapToCategoria (CategoriaDto categoriaDto);

	CategoriaDto mapToPostDto (Categoria categoria);

	CategoriaDto mapToGetDto(Categoria categoria);

}
