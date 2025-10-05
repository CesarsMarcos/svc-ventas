package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.CategoriaGetDto;
import com.svc.ventas.models.mapstruct.dto.CategoriaPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Categoria;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

	@Mapping(target = "indEstado", constant = "true")
	Categoria mapToEntity (CategoriaPostDto categoriaDto);

	Categoria mapToCategoria (CategoriaGetDto categoriaDto);

	CategoriaPostDto mapToPostDto (Categoria categoria);

	CategoriaGetDto mapToGetDto(Categoria categoria);

}
