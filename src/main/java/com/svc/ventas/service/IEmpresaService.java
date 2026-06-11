package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.request.EmpresaPostRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpresaService {

	List<EmpresaGetDto> listar();

	EmpresaGetDto obtener(Integer id);

	@Transactional
	Response guardar(EmpresaPostRequest empresa);

	@Transactional
	Response modificar(Integer id, EmpresaPostRequest empresa);

	Response cambiarAplicacionImpuesto(Integer id, Boolean aplica);

	void eliminar(Integer id);

}
