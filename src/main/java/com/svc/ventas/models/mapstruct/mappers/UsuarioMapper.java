package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.UsuarioCreateRequest;
import com.svc.ventas.message.response.UsuarioSearchResponse;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.entity.Rol;
import com.svc.ventas.models.entity.Sucursal;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "idUsuario", ignore = true)
	@Mapping(target = "empleado", source = "empleado")
	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "indEstado", constant = "true")
	@Mapping(target = "fecAdd", ignore = true)
	@Mapping(target = "fecUpdate", ignore = true)
	Usuario mapToUsuario(UsuarioCreateRequest request,
											 Empleado empleado,
											 List<Rol> roles);

	@Mapping(target = "clave", ignore = true)
	UsuarioDto map (Usuario usuario);

	@Mapping(source = "idUsuario", target = "idUsuario")
	@Mapping(target = "nombreCompleto", expression = "java(usuario.getEmpleado().getPersona().getNombre().concat(\" \").concat(usuario.getEmpleado().getPersona().getApePaterno()))")
	@Mapping(target = "tipoDocumento", source = "empleado.persona.tipoDocumento")
	@Mapping(target = "numDocumento", source = "empleado.persona.numDocumento")
	@Mapping(target = "usuario", source = "usuario")
	//@Mapping(target = "roles", source = "roles")
	@Mapping(target = "estado", source = "indEstado")
	UsuarioSearchResponse mapToSearch (Usuario usuario);
	
}
