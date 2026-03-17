package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.request.UsuarioCreateRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {

	List<UsuarioDto> lista();

	@Transactional
	Response agregar(UsuarioCreateRequest usuario);

	@Transactional
	Response modificar(Integer id, UsuarioDto usuario);

	UsuarioDto obtener(int id);

	void eliminar(int id);

	Boolean isSaved(Integer idEmpleado);

	Usuario getPorUserName(String username);

}
