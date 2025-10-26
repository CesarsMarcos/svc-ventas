package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpleadoService {

	List<EmpleadoDto> lista();

	@Transactional
	Response agregar(EmpleadoDto empleado);

	@Transactional
	Response modificar(int id, EmpleadoDto empleado);
	
	EmpleadoDto obtener (int id);
	
	void eliminar(int id);

	Boolean isSaved (String documento);
	
}
