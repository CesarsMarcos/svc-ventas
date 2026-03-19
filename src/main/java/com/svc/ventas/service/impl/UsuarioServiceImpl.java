package com.svc.ventas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.message.request.UsuarioCreateRequest;
import com.svc.ventas.message.response.UsuarioSearchResponse;
import com.svc.ventas.models.dao.EmpleadoRepo;
import com.svc.ventas.models.dao.RolRepo;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.entity.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.UsuarioRepo;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import com.svc.ventas.models.mapstruct.mappers.EmpleadoMapper;
import com.svc.ventas.models.mapstruct.mappers.UsuarioMapper;
import com.svc.ventas.service.IUsuarioService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

	private final UsuarioRepo usuarioRepo;

	private final EmpleadoRepo empleadoRepo;

	private final RolRepo rolRepo;

	private final UsuarioMapper usuarioMapper;
	
	private final EmpleadoMapper empleadoMapper;

	@Override
	public List<UsuarioDto> lista() {
		 return usuarioRepo.getUsuariosActivos()
				 .stream()
				 .map(usuarioMapper::map)
		.collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> usuarios(String nombre, String documento,int page, int size) {

		String filtro = (nombre != null && !nombre.isBlank()) ? nombre.trim().toLowerCase() : "";

		Pageable pageable = PageRequest.of(page, size);

		Page<UsuarioSearchResponse> pageUsuario = buscarPorNombreOCodigo(filtro, pageable);

		return Map.of(
						"usuarios", pageUsuario.getContent(),
						"currentPage", pageUsuario.getNumber(),
						"pageSize", pageUsuario.getSize(),
						"totalItems", pageUsuario.getTotalElements(),
						"totalPages", pageUsuario.getTotalPages(),
						"empty", pageUsuario.isEmpty()
		);
	}

	@Override
	public Response agregar(UsuarioCreateRequest usuarioRequest) {

		Empleado empleado = empleadoRepo.findById(usuarioRequest.getIdEmpleado())
						.orElseThrow(() -> new EntityNotFoundException(
										String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", usuarioRequest.getIdEmpleado())));

		List<Rol> roles = rolRepo.findAllById(usuarioRequest.getRoles());
		if (roles.size() != usuarioRequest.getRoles().size()) {
			throw new BusinessException("Roles enviados no existen");
		}

		if(usuarioRepo.existsByEmpleadoIdEmpleado(usuarioRequest.getIdEmpleado())){
			throw new ConflictException("El usuario ya fue registrado");
		}

		if(usuarioRepo.findByUsuario(usuarioRequest.getUsuario()).isPresent()){
			throw new BusinessException("Solo debe existir un usuario registrado");
		}

		Usuario usuario = usuarioMapper.mapToUsuario(usuarioRequest, empleado, roles);
		usuario.setClave(new BCryptPasswordEncoder().encode(usuarioRequest.getClave()));

		usuarioRepo.save(usuario);
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public Response modificar(Integer id, UsuarioDto usuarioDto) {
		usuarioRepo.findById(id)
				.map(usuario-> {
					usuario.setEmpleado(empleadoMapper.mapToEmpleado(usuarioDto.getEmpleado()));
					return usuarioRepo.save(usuario);
				})
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Usuario", id)));
		return Response
				.builder()
				.mensaje(Constantes.MENSAJE_SAVE)
				.build();
	}

	@Override
	public UsuarioDto obtener(int id) {
		return  usuarioRepo.findById(id)
				.map(usuarioMapper::map)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Usuario", id)));
	}

	@Override
	public void eliminar(int id) {

	}

	@Override
	public Boolean isSaved(Integer idEmpleado) {
		return usuarioRepo.existsByEmpleadoIdEmpleado(idEmpleado);
	}

	@Override
	public Usuario getPorUserName(String username) {
		return usuarioRepo.findByUsuario(username)
				//.map(usuarioMapper::map)
				.orElseThrow(() ->
						new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Usuario", username)));
	}

	private Page<UsuarioSearchResponse> buscarPorNombreOCodigo(String termino, Pageable pageable) {
		return usuarioRepo.findUsuario(termino, pageable)
						.map(usuarioMapper::mapToSearch);
	}

}
