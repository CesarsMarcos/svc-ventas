package com.svc.ventas.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.svc.ventas.message.request.EmpleadoCreateRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoListDto;
import com.svc.ventas.models.mapstruct.dto.PersonaEmpleadoDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpleadoService {

	List<EmpleadoDto> lista();

	Map<String, Object> empleadosNoUsuario(String nombre, String documento, int page, int size);

	List<PersonaEmpleadoDto> empleadosNoUsuario();

	@Transactional
	Response agregar(EmpleadoCreateRequest empleadoRequest);

	@Transactional
	Response modificar(int id, EmpleadoDto empleado);
	
	EmpleadoGetDto obtener (int id);
	
	void eliminar(int id);

	Boolean isSaved (String documento);
	
}
