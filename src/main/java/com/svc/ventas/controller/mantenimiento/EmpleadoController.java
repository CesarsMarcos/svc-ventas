package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.EmpleadoCreateRequest;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import jakarta.validation.Valid;

import com.svc.ventas.service.IEmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/empleados/")
public class EmpleadoController {

  private final IEmpleadoService empleadoService;

  @GetMapping
  @PreAuthorize("hasAuthority('LISTAR_EMPLEADOS')")
  public ResponseEntity<?> empleados() {
    return new ResponseEntity<>(empleadoService.lista(), HttpStatus.OK);
  }

  @GetMapping("searchEmpleadoNoUsuarios")
  @PreAuthorize("hasAuthority('SEARCH_EMPLEADOS')")
  public ResponseEntity<Map<String, Object>> search(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) String documento,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size) {
    Map<String, Object> response = empleadoService.empleadosNoUsuario(nombre, documento, page, size);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CREAR_EMPLEADOS')")
  public ResponseEntity<Response> agregar(@Valid @RequestBody EmpleadoCreateRequest empleado) {
    Response response = empleadoService.agregar(empleado);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('EDITAR_EMPLEADOS')")
  public ResponseEntity<Response> modificar(@PathVariable Integer id, @RequestBody @Valid EmpleadoDto empleado) {
    Response response = empleadoService.modificar(id, empleado);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('VER_EMPLEADOS')")
  public ResponseEntity<?> obtener(@PathVariable Integer id) {
    return new ResponseEntity<>(empleadoService.obtener(id), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('ELIMINAR_EMPLEADOS')")
  public ResponseEntity<Void> eliminar(@PathVariable int id) {
    empleadoService.obtener(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("{id}/update-estado")
  @PreAuthorize("hasAuthority('EDITAR_ESTADO_EMPLEADOS')")
  public ResponseEntity<Void> updateEstado(@PathVariable Integer idEmpleado) {
    empleadoService.updateEstado(idEmpleado);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
