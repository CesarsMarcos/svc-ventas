package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.models.mapstruct.dto.PersonaListDto;
import org.springframework.transaction.annotation.Transactional;

public interface IPersonaService {

	List<PersonaDto> personas ();

	List<PersonaListDto> personasNoEmpleados();

	List<PersonaListDto> personasNoClientes();

	@Transactional
	Response guardar(PersonaDto persona);

	@Transactional
	Response modificar (Integer id, PersonaDto persona);

	PersonaDto obtener (Integer id);

	Boolean isSaved (String documento);

}
