package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import org.springframework.transaction.annotation.Transactional;

public interface ICategoriaService {

	List<CategoriaDto> lista();

	@Transactional
	Response guardar(CategoriaDto categoria);

	@Transactional
	Response modificar(int id, CategoriaDto categoria);

	CategoriaDto obtener(int id);
}
