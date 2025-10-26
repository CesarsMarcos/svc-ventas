package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {

	List<UsuarioDto> lista();

	@Transactional
	Response agregar(UsuarioDto usuario);

	@Transactional
	Response modificar(Integer id, UsuarioDto usuario);

	UsuarioDto obtener(int id);

	void eliminar(int id);

	Boolean isSaved(Integer idEmpleado);

	UserDetailsService userDetailsService();

	UsuarioDto getPorUserName(String username);

}
