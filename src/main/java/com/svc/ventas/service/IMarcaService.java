package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.MarcaGetDto;
import com.svc.ventas.models.mapstruct.dto.MarcaPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface IMarcaService {

	List<MarcaGetDto> lista();

	MarcaGetDto obtener(Integer id);

	@Transactional
	Response guardar(MarcaPostDto marca);

	@Transactional
	Response modificar(Integer id,MarcaPostDto marca);

	void eliminar(int id);

}
