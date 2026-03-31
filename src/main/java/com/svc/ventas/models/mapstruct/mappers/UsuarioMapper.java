package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.SearchUsuarioResponse;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "clave", ignore = true)
	UsuarioDto map (Usuario usuario);

	@Mapping(source = "idUsuario", target = "idUsuario")
	@Mapping(target = "nombreCompleto", expression = "java(getNombreCompleto(usuario))")
	@Mapping(target = "tipoDocumento", expression = "java(getTipoDocumento(usuario))")
	@Mapping(target = "numDocumento", expression = "java(getNumeroDocumento(usuario))")
  @Mapping(target = "roles", expression = "java(usuario.getRoles().stream().map(Rol::getDesRol).toArray(String[]::new))")
  @Mapping(target = "usuario", source = "usuario")
	@Mapping(target = "estado", source = "indEstado")
  SearchUsuarioResponse mapToSearch (Usuario usuario);

	default String getNombreCompleto(Usuario usuario) {
    return usuario.getPersona().getNombre().concat(" ".concat(usuario.getPersona().getApePaterno()));
	}

	default String getTipoDocumento(Usuario usuario) {
		return usuario.getPersona().getTipoDocumento().getLabel();
	}

	default String getNumeroDocumento(Usuario usuario) {
		return usuario.getPersona().getNumDocumento();
	}
	
}
