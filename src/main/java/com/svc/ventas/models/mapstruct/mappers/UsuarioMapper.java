package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.UsuarioSearchResponse;
import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoDocumentoPersona;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.mapstruct.Mapping;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "clave", ignore = true)
	UsuarioDto map (Usuario usuario);

	@Mapping(source = "idUsuario", target = "idUsuario")
	@Mapping(target = "nombreCompleto", expression = "java(getNombreCompleto(usuario))")
	@Mapping(target = "tipoDocumento", expression = "java(getTipoDocumento(usuario))")
	@Mapping(target = "numDocumento", expression = "java(getNumeroDocumento(usuario))")
	@Mapping(target = "usuario", source = "usuario")
	@Mapping(target = "estado", source = "indEstado")
	UsuarioSearchResponse mapToSearch (Usuario usuario);

	default String getNombreCompleto(Usuario usuario) {
		String nombre;
		if (usuario.getSucursal().getEmpresa().getIsUsaEmpleados()) {
			nombre = usuario.getEmpleado().getPersona().getNombre().concat(" ".concat(usuario.getPersona().getApePaterno()));
		} else {
			nombre = usuario.getPersona().getNombre().concat(" ".concat(usuario.getPersona().getApePaterno()));
		}
		return nombre;
	}

	default String getTipoDocumento(Usuario usuario) {
		String tipoDocumento;
		if (Objects.isNull(usuario.getEmpleado())) {
			tipoDocumento = usuario.getPersona().getTipoDocumento().getLabel();
		} else {
			tipoDocumento = usuario.getEmpleado().getPersona().getTipoDocumento().getLabel();
		}
		return tipoDocumento;
	}

	default String getNumeroDocumento(Usuario usuario) {
		String nombre;
		if (Objects.isNull(usuario.getEmpleado())) {
			nombre = usuario.getPersona().getNumDocumento();
		} else {
			nombre = usuario.getEmpleado().getPersona().getNumDocumento();
		}
		return nombre;
	}
	
}
