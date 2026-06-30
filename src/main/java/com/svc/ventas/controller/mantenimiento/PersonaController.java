package com.svc.ventas.controller.mantenimiento;

import java.util.Map;

import com.svc.ventas.message.request.PersonaRequest;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.service.IPersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.svc.ventas.message.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/personas/")
public class PersonaController {

  private final IPersonaService personaService;

  private final PersonaMapper personaMapper;

  @GetMapping
  @PreAuthorize("hasAuthority('LISTA_PERSONAS')")
  public ResponseEntity<?> personas() {
    return new ResponseEntity<>(personaService.personasNoUsuarios(), HttpStatus.OK);
  }

  @GetMapping("no-empleados")
  @PreAuthorize("hasAuthority('LISTA_PERSONAS')")
  public ResponseEntity<?> noEmpleados() {
    return new ResponseEntity<>(personaService.personasNoEmpleados(), HttpStatus.OK);
  }

  @GetMapping("no-clientes")
  @PreAuthorize("hasAuthority('LISTA_PERSONAS')")
  public ResponseEntity<?> noClientes() {
    return new ResponseEntity<>(personaService.personasNoClientes(), HttpStatus.OK);
  }

  @GetMapping("personas-emplados-para-usuarios")
  @PreAuthorize("hasAuthority('LISTA_PERSONAS')")
  public ResponseEntity<?> personasEmpleadoDisponibleParaUsuario() {
    return new ResponseEntity<>(personaService.listaPersonaEmpleadoSegunEmpresa(), HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CREAR_PERSONAS')")
  public ResponseEntity<Response> guardar(@Valid @RequestBody PersonaRequest persona) {
    return new ResponseEntity<>(personaService.guardar(persona), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('EDITAR_PERSONAS')")
  public ResponseEntity<Response> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaDto persona) {
    Response response = personaService.modificar(id, persona);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('VER_PERSONAS')")
  public ResponseEntity<PersonaDto> obtener(@PathVariable Integer id) {
    PersonaDto personaSave = personaService.obtener(id);
    return new ResponseEntity<>(personaSave, HttpStatus.OK);
  }

  @GetMapping("searchPersona")
  @PreAuthorize("hasAuthority('VER_PERSONAS')")
  public ResponseEntity<Map<String, Object>> searchPersona(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) String documento,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size) {

    nombre = (nombre != null && !nombre.isBlank()) ? nombre.trim() : null;
    documento = (documento != null && !documento.isBlank()) ? documento.trim() : null;

    Map<String, Object> response = personaService.searchPersona(documento, nombre, page, size);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
