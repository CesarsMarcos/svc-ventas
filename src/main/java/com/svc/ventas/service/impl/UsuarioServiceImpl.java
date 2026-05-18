package com.svc.ventas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.message.request.UsuarioCreateRequest;
import com.svc.ventas.message.response.MenuResponse;
import com.svc.ventas.message.response.SearchUsuarioResponse;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
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

  private final PersonaRepository personaRepo;

  private final RolRepo rolRepo;

  private final UsuarioMapper usuarioMapper;

  private final EmpleadoMapper empleadoMapper;

  private final MenuRepository menuRepo;

  private final AppContext appContext;

  @Override
  public List<UsuarioDto> lista() {
    Empresa empresa = appContext.getEmpresa();
    return usuarioRepo.getUsuariosActivos(empresa)
            .stream()
            .map(usuarioMapper::map)
            .collect(Collectors.toList());
  }

  @Override
  public Map<String, Object> usuarios(String nombre, String documento, int page, int size) {

    String filtro = (nombre != null && !nombre.isBlank()) ? nombre.trim().toLowerCase() : "";

    Pageable pageable = PageRequest.of(page, size);

    Page<SearchUsuarioResponse> pageUsuario = buscarPorNombreOCodigo(filtro, pageable);

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
  public Response agregar(UsuarioCreateRequest request) {

    Empresa empresa = appContext.getEmpresa();

    Empleado empleado = null;
    Persona persona;

    if (empresa.getIsUsaEmpleados()) {

      empleado = empleadoRepo.findById(request.getId())
              .orElseThrow(() -> new EntityNotFoundException(
                      String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", request.getId())
              ));

      if (usuarioRepo.existsByEmpleadoIdEmpleado(request.getId())) {
        throw new ConflictException("El empleado ya tiene usuario");
      }

      persona = empleado.getPersona();

    } else {

      persona = personaRepo.findById(request.getId())
              .orElseThrow(() -> new EntityNotFoundException(
                      String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", request.getId())
              ));

      if (usuarioRepo.existsByPersonaIdPersona(request.getId())) {
        throw new ConflictException("La persona ya tiene usuario");
      }
    }

    List<Rol> roles = rolRepo.findAllById(request.getRoles());
    if (roles.size() != request.getRoles().size()) {
      throw new BusinessException("Roles enviados no existen");
    }

    if (usuarioRepo.getByUserName(request.getUsuario()).isPresent()) {
      throw new BusinessException("El usuario ya existe");
    }

    Usuario usuario = new Usuario();
    usuario.setUsuario(request.getUsuario());
    usuario.setClave(new BCryptPasswordEncoder().encode(request.getClave()));
    usuario.setIndEstado(Constantes.IND_ACTIVO);
    usuario.setRoles(roles);

    usuario.setPersona(persona);
    usuario.setEmpleado(empleado);

    if (empresa.getIsUsaEmpleados()) {
      usuario.setSucursal(empleado.getSucursal());
    } else {
      usuario.setSucursal(appContext.getSucursal());
    }

    usuarioRepo.save(usuario);

    return Response.builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Response modificar(Integer id, UsuarioDto usuarioDto) {
    usuarioRepo.findById(id)
            .map(usuario -> {
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
  public void modifyEstado(Integer id) {

  }

  @Override
  public UsuarioDto obtener(int id) {
    return usuarioRepo.findById(id)
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

  private Page<SearchUsuarioResponse> buscarPorNombreOCodigo(String termino, Pageable pageable) {
    return usuarioRepo.findUsuario(termino, pageable)
            .map(usuarioMapper::mapToSearch);
  }

  @Override
  public List<MenuResponse> getMenusPorUsuario(Usuario usuario) {

    Empresa empresa = usuario.getSucursal().getEmpresa();

    Predicate<Menu> conEmpleados = menu -> empresa.getIsUsaEmpleados() | Boolean.FALSE.equals(menu.getIsEmpleado());

    List<Menu> menus = menuRepo.listarMenuPorUsuario(usuario.getUsuario())
            .stream().filter(conEmpleados)
            .collect(Collectors.toList());

    return getMenuResponseList(menus);
  }

  @Override
  public Usuario getUsuarioPorUserName(String userName) {
    return usuarioRepo.getByUserName(userName)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Usuario", userName)));
  }

  private List<MenuResponse> getMenuResponseList(List<Menu> menus) {

    Map<Integer, MenuResponse> map = new HashMap<>();
    List<MenuResponse> roots = new ArrayList<>();

    for (Menu m : menus) {
      MenuResponse dto = new MenuResponse();
      dto.setName(m.getNombre());
      dto.setIcon(m.getIcono());
      dto.setRouteLink(m.getUrl());
      dto.setSubmenus(new ArrayList<>());

      map.put(m.getIdMenu(), dto);
    }

    for (Menu m : menus) {
      MenuResponse dto = map.get(m.getIdMenu());

      if (m.getIdMenuPadre() == null) {
        roots.add(dto);
      } else {
        MenuResponse padre = map.get(m.getIdMenuPadre().getIdMenu());
        if (padre != null) {
          padre.getSubmenus().add(dto);
        }
      }
    }

    return roots;
  }

}
