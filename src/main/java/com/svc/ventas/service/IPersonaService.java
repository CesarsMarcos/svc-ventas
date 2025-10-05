package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.PersonaGetDto;
import com.svc.ventas.models.mapstruct.dto.PersonaPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface IPersonaService {

	List<PersonaGetDto> personas ();

	@Transactional
	Response guardar(PersonaPostDto persona);

	@Transactional
	Response modificar (Integer id, PersonaPostDto persona);

	PersonaGetDto obtener (Integer id);

	List<PersonaGetDto> personasPorTipo(String tipo);

	Boolean isSaved (String documento);

}
