package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.EmpleadoGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpleadoService {

	List<EmpleadoGetDto> lista();

	@Transactional
	Response agregar(EmpleadoPostDto empleado);

	@Transactional
	Response modificar(int id,EmpleadoPostDto empleado);
	
	EmpleadoGetDto obtener (int id);
	
	void eliminar(int id);

	Boolean isSaved (String documento);
	
}
