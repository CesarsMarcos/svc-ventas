package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.EmpleadoSearchResponse;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoListDto;
import com.svc.ventas.models.mapstruct.dto.PersonaEmpleadoDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Empleado;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

	EmpleadoDto mapToEmpleadoDto(Empleado empleado);

	@Mapping(target = "idEmpleado", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Empleado mapToEmpleado(EmpleadoDto empleadoDto);

	@Mapping(target = "persona", source = "persona")
	@Mapping(target = "sucursal", source = "sucursal")
	@Mapping(target = "indEstado", constant = "true")
	@Mapping(target = "fecAdd", ignore = true)
	@Mapping(target = "fecUpdate", ignore = true)
	Empleado mapEmpleadoRequestToEmpleado(Persona persona, Sucursal sucursal);

	@Mapping(source = "idEmpleado", target = "id")
	@Mapping(source = "empleado.persona.nombreCompleto", target = "nombreCompleto")
	PersonaEmpleadoDto mapPersonaEmpleado(Empleado empleado);

	@Mapping(source = "empleado.persona.nombreCompleto", target = "nombreCompleto")
	@Mapping(target = "tipoDocumento", source = "persona.tipoDocumento")
	@Mapping(target = "numDocumento", source = "persona.numDocumento")
	@Mapping(target = "correo", source = "persona.correo")
	@Mapping(target = "fechaNacimiento", source = "persona.fechaNacimiento")
	@Mapping(target = "celular", source = "persona.celular")
	@Mapping(target = "foto", source = "persona.foto")
	@Mapping(target = "razonSocial", source = "sucursal.razonSocial")
	@Mapping(target = "representante", source = "sucursal.representante")
	@Mapping(target = "estado", source = "indEstado")
	EmpleadoGetDto mapToDto(Empleado empleado);

	@Mapping(source = "idEmpleado", target = "idEmpleado")
	@Mapping(source = "codEmpleado", target = "codEmpleado")
	@Mapping(source = "empleado.persona.nombreCompleto", target = "nombreCompleto")
	@Mapping(target = "tipoDocumento", source = "persona.tipoDocumento")
	@Mapping(target = "numDocumento", source = "persona.numDocumento")
	@Mapping(target = "celular", source = "persona.celular")
	@Mapping(target = "sucursal", source = "sucursal.razonSocial")
	@Mapping(target = "estado", source = "indEstado")
	EmpleadoSearchResponse mapToSearch (Empleado empleado);

}
