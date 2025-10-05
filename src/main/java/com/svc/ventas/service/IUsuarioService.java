package com.svc.ventas.service;

import java.util.List;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.UsuarioGetDto;
import com.svc.ventas.models.mapstruct.dto.UsuarioPostDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {

	List<UsuarioGetDto> lista();

	@Transactional
	Response agregar(UsuarioPostDto usuario);

	@Transactional
	Response modificar(Integer id, UsuarioPostDto usuario);

	UsuarioGetDto obtener(int id);

	void eliminar(int id);

	Boolean isSaved(Integer idEmpleado);

	UserDetailsService userDetailsService();

	UsuarioGetDto getPorUserName(String username);

}
