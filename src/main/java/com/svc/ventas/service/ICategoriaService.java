package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ICategoriaService {

	List<CategoriaDto> lista();

	Map<String, Object> searchCategorias(String nombre, Pageable pageable) ;

	List<CategoriaDto> categoriasPorProductoStock();

	@Transactional
	Response guardar(CategoriaDto categoria);

	@Transactional
	Response modificar(int id, CategoriaDto categoria);

	CategoriaDto obtener(int id);

}
