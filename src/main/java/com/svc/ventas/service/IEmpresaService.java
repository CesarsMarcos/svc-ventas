package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpresaPostDto;
import org.springframework.transaction.annotation.Transactional;

public interface IEmpresaService {

	List<Empresa> listar();

	EmpresaGetDto obtener(Integer id);

	@Transactional
	Response guardar(EmpresaPostDto global);

	@Transactional
	Response modificar(Integer id, EmpresaPostDto global);

	void eliminar(Integer id);

}
