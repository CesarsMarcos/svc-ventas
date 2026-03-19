package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.EmpleadoCreateRequest;
import com.svc.ventas.models.mapstruct.dto.EmpleadoDto;
import jakarta.validation.Valid;

import com.svc.ventas.service.IEmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<?> empleados() {
    return new ResponseEntity<>(empleadoService.lista(), HttpStatus.OK);
  }

  @GetMapping("searchEmpleadoNoUsuarios")
  public ResponseEntity<Map<String, Object>> search(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) String documento,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size) {
    Map<String, Object> response = empleadoService.empleadosNoUsuario(nombre, documento, page, size);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Response> agregar(@Valid @RequestBody EmpleadoCreateRequest empleado) {
    Response response = empleadoService.agregar(empleado);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("{id}")
  public ResponseEntity<Response> modificar(@PathVariable Integer id, @RequestBody @Valid EmpleadoDto empleado) {
    Response response = empleadoService.modificar(id, empleado);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> obtener(@PathVariable Integer id) {
    return new ResponseEntity<>(empleadoService.obtener(id), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> eliminar(@PathVariable int id) {
    empleadoService.obtener(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
