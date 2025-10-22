package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpresaService {

	List<Empresa> listar();

	EmpresaDto obtener(Integer id);

	@Transactional
	Response guardar(EmpresaDto global);

	@Transactional
	Response modificar(Integer id, EmpresaDto global);

	void eliminar(Integer id);

}
