package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoListDto;
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

	@Mapping(source = "idEmpleado", target = "idEmpleado")
	@Mapping(source = "persona.nombre", target = "nombre")
	EmpleadoListDto mapToEmpleado(Empleado empleado);

}
