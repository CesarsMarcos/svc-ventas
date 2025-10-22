package com.svc.ventas.models.mapstruct.mappers;

import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "idUsuario", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Usuario mapToUsuario (UsuarioDto usuarioDto);

	Usuario mapToUsuarioGet (UsuarioDto usuarioDto);

	@Mapping(target = "clave", ignore = true)
	UsuarioDto map (Usuario usuario);
	
}
