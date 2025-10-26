package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.MarcaDto;
import org.springframework.transaction.annotation.Transactional;

public interface IMarcaService {

	List<MarcaDto> lista();

	MarcaDto obtener(Integer id);

	@Transactional
	Response guardar(MarcaDto marca);

	@Transactional
	Response modificar(Integer id,MarcaDto marca);

	void eliminar(int id);

}
