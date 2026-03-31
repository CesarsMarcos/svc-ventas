package com.svc.ventas.service;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.UsuarioCreateRequest;
import com.svc.ventas.message.response.MenuResponse;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {

	List<UsuarioDto> lista();

	Map<String, Object> usuarios(String nombre, String documento,int page, int size);

	@Transactional
	Response agregar(UsuarioCreateRequest usuario);

	@Transactional
	Response modificar(Integer id, UsuarioDto usuario);

	UsuarioDto obtener(int id);

	void eliminar(int id);

	Boolean isSaved(Integer idEmpleado);

	List<MenuResponse> getMenusPorUsuario(Usuario userName);

	Usuario getUsuarioPorUserName(String userName);

}
