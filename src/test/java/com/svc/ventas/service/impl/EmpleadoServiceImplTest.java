package com.svc.ventas.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.EmpleadoRepo;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import com.svc.ventas.models.mapstruct.mappers.EmpleadoMapper;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import com.svc.ventas.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImplTest {

  @Mock
  EmpleadoRepo empleadoRepo;

  //@Mock
  //private EmpleadoMapper empleadoMapper;

  //@Mock
  //private PersonaMapper personaMapper;

  @Spy
  private EmpleadoMapper empleadoMapper = Mappers.getMapper(EmpleadoMapper.class);

  @Spy
  private PersonaMapper personaMapper = Mappers.getMapper(PersonaMapper.class);

  @InjectMocks
  EmpleadoServiceImpl empleadoService;

  @Test
  @DisplayName("Valida que la actualizacion sea exitosa")
  void createUser_Valid_ReturnsDto() {

    Integer id = 15;

    Empleado empleado = JsonUtils.fromJsonFile("empleado-request.json", Empleado.class);
     EmpleadoDto empleadoPostDto = JsonUtils.fromJsonFile("persona-request-dto.json", EmpleadoDto.class);

    when(empleadoMapper.mapToEmpleado(empleadoPostDto)).thenReturn(empleado);
    when(empleadoRepo.findById(id)).thenReturn(Optional.of(empleado));
    when(empleadoRepo.save(empleadoMapper.mapToEmpleado(empleadoPostDto))).thenReturn(empleado);

    Response response = empleadoService.modificar(id, empleadoPostDto);

    assertEquals(":: Registro modificado con éxito", response.getMensaje());

  }

  @Test
  @DisplayName("Retorna 404 al modificar")
  void getUserById_NotFound_ThrowsNotFound() {
    Integer id = 15;

    Empleado empleado = JsonUtils.fromJsonFile("empleado-request.json", Empleado.class);
    EmpleadoDto empleadoPostDto = JsonUtils.fromJsonFile("persona-request-dto.json", EmpleadoDto.class);

    when(empleadoRepo.findById(id)).thenReturn(Optional.of(empleado));

    var ex = assertThrows(EntityNotFoundException.class,
            ()-> empleadoService.modificar(id, empleadoPostDto));

    assertEquals(":: No existe Empleado para el ID: 15 ingresado", ex.getMessage());

  }

}