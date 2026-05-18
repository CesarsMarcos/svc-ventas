package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.PersonaRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.models.mapstruct.dto.PersonaEmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.PersonaListDto;
import org.springframework.transaction.annotation.Transactional;

public interface IPersonaService {

	List<?> listaPersonaEmpleadoSegunEmpresa();

	List<PersonaEmpleadoDto> personasNoUsuarios ();

	List<PersonaListDto> personasNoEmpleados();

	List<PersonaListDto> personasNoClientes();

	@Transactional
	Response guardar(PersonaRequest request);

	@Transactional
	Response modificar (Integer id, PersonaDto persona);

	PersonaDto obtener (Integer id);

	Map<String, Object> searchPersona(String documento, String nombre, int page, int size);
}
