package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Empleado;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

	EmpleadoDto mapToEmpleadoDto(Empleado empleado);

	@Mapping(target = "idEmpleado", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Empleado mapToEmpleado(EmpleadoDto empleadoDto);

}
