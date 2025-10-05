package com.svc.ventas.service;

import java.util.List;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.TipoPersona;
import com.svc.ventas.models.mapstruct.dto.TipoPersonaDto;
import org.springframework.transaction.annotation.Transactional;

public interface ITIpoPersonaService {

	List<TipoPersona> lista();

	@Transactional
	Response agregar (TipoPersona tipo);

	@Transactional
	Response modificar(int id, TipoPersona tipo);

	TipoPersonaDto obtener(int id);
	
	void eliminar(int id);
	
}
