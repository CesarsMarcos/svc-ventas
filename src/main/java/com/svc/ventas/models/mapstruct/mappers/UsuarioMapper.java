package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.UsuarioPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioGetDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "indEstado", constant = "true")
	Usuario mapToUsuario (UsuarioPostDto usuarioDto);

	Usuario mapToUsuarioGet (UsuarioGetDto usuarioDto);
	
	UsuarioPostDto mapToUsuarioDto (Usuario usuario);

	UsuarioGetDto map (Usuario usuario);
	
}
