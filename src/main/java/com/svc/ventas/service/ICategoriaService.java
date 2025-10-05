package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CategoriaGetDto;
import com.svc.ventas.models.mapstruct.dto.CategoriaPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface ICategoriaService {

	List<CategoriaGetDto> lista();

	@Transactional
	Response guardar(CategoriaPostDto categoria);

	@Transactional
	Response modificar(int id, CategoriaPostDto categoria);

	CategoriaGetDto obtener(int id);
}
