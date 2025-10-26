package com.svc.ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.UsuarioRepo;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import com.svc.ventas.models.mapstruct.mappers.EmpleadoMapper;
import com.svc.ventas.models.mapstruct.mappers.SucursalMapper;
import com.svc.ventas.models.mapstruct.mappers.UsuarioMapper;
import com.svc.ventas.service.IUsuarioService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

	private final UsuarioRepo usuarioRepo;

	private final UsuarioMapper usuarioMapper;
	
	private final EmpleadoMapper empleadoMapper;
	
	private final SucursalMapper sucursalMapper;

	@Override
	public List<UsuarioDto> lista() {
		 return usuarioRepo.getUsuariosActivos()
				 .stream()
				 .map(usuarioMapper::map)
		.collect(Collectors.toList());
	}

	@Override
	public Response agregar(UsuarioDto usuarioDto) {
		Usuario usuario = usuarioMapper.mapToUsuario(usuarioDto);
		usuario.setClave(new BCryptPasswordEncoder().encode(usuarioDto.getClave()));
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
					usuario.setSucursal(sucursalMapper.mapToSucursalGet(usuarioDto.getSucursal()));
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
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return usuarioRepo.findByUsuario(username)
						.orElseThrow(() ->
								new UsernameNotFoundException("usuario no encontrado en Base de datos"));
			}
		};
	}

	@Override
	public UsuarioDto getPorUserName(String username) {
		return usuarioRepo.findByUsuario(username)
				.map(usuarioMapper::map)
				.orElseThrow(() ->
						new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Usuario", username)));
	}

}
