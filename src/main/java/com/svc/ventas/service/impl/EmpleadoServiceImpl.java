package com.svc.ventas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.message.request.EmpleadoCreateRequest;
import com.svc.ventas.message.response.EmpleadoSearchResponse;
import com.svc.ventas.models.dao.PersonaRepository;
import com.svc.ventas.models.dao.SucursalRepo;
import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.dto.EmpleadoGetDto;
import com.svc.ventas.models.mapstruct.dto.PersonaEmpleadoDto;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.EmpleadoRepo;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.mapstruct.mappers.EmpleadoMapper;
import com.svc.ventas.service.IEmpleadoService;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {

  private final EmpleadoRepo empleadoRepo;

  private final PersonaRepository personaRepo;

  private final SucursalRepo sucursalRepo;

  private final EmpleadoMapper empleadoMapper;

  private final PersonaMapper personaMapper;

  @Override
  public List<EmpleadoDto> lista() {
    return empleadoRepo.empleadosQueNoTienenUsuario()
            .stream()
            .map(empleadoMapper::mapToEmpleadoDto)
            .collect(Collectors.toList());
  }

  @Override
  public List<PersonaEmpleadoDto> empleadosNoUsuario() {
    return empleadoRepo.empleadosQueNoTienenUsuario()
            .stream()
            .map(empleadoMapper::mapPersonaEmpleado)
            .collect(Collectors.toList());
  }

  @Override
  public Map<String, Object> empleadosNoUsuario(String nombre, String documento, int page, int size) {

    String filtro = (nombre != null && !nombre.isBlank()) ? nombre.trim().toLowerCase() : "";

    Pageable pageable = PageRequest.of(page, size);

    Page<EmpleadoSearchResponse> pageEmpleado = buscarPorNombreOCodigo(filtro, pageable);

    return Map.of(
            "empleados", pageEmpleado.getContent(),
            "currentPage", pageEmpleado.getNumber(),
            "pageSize", pageEmpleado.getSize(),
            "totalItems", pageEmpleado.getTotalElements(),
            "totalPages", pageEmpleado.getTotalPages(),
            "empty", pageEmpleado.isEmpty()
    );

  }

  @Override
  public Response agregar(EmpleadoCreateRequest empleadoRequest) {

    Persona persona = personaRepo.findById(empleadoRequest.getIdPersona())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Persona", empleadoRequest.getIdPersona())));

    if (empleadoRepo.existsByPersonaIdPersona(empleadoRequest.getIdPersona())) {
      throw new ConflictException("El empleado ya fue registrado");
    }

    Sucursal sucursal = sucursalRepo.findById(empleadoRequest.getIdSucursal())
            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));

    empleadoRepo.save(empleadoMapper.mapEmpleadoRequestToEmpleado(persona, sucursal));
    return Response.builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Response modificar(int id, EmpleadoDto empleadoPostDto) {
    empleadoRepo.findById(id)
            .map(empleado -> {
              empleado.setPersona(personaMapper.mapToPersona(empleadoPostDto.getPersona()));
              return empleadoRepo.save(empleado);
            })
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
    return Response.builder()
            .mensaje(Constantes.MENSAJE_MOD)
            .build();
  }

  @Override
  public EmpleadoGetDto obtener(int id) {
    return empleadoRepo.findById(id)
            .map(empleadoMapper::mapToDto)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
  }

  @Override
  public void eliminar(int id) {
    Empleado empleadoSave = empleadoRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Empleado", id)));
    empleadoSave.setIndEstado(Constantes.IND_INACTIVO);
    empleadoRepo.save(empleadoSave);
  }

  private Page<EmpleadoSearchResponse> buscarPorNombreOCodigo(String termino, Pageable pageable) {
    return empleadoRepo.findEmpleadosQueNoTienenUsuario(termino, pageable)
            .map(empleadoMapper::mapToSearch);
  }

}
