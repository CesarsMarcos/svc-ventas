package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.mapstruct.dto.EmpleadoGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoPostDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Empleado;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

	EmpleadoGetDto mapToEmpleadoDto(Empleado empleado);

	@Mapping(target = "indEstado", constant = "true")
	Empleado mapToEmpleado(EmpleadoPostDto empleadoDto);

	Empleado mapToEmpleado(EmpleadoGetDto empleadoDto);

}
