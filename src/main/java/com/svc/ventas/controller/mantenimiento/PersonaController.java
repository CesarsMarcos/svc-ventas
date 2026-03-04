package com.svc.ventas.controller.mantenimiento;

import java.util.List;
import java.util.Map;

import com.svc.ventas.models.entity.Persona;
import com.svc.ventas.models.mapstruct.mappers.PersonaMapper;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.service.IPersonaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<?> personas() {
    return new ResponseEntity<>(personaService.personas(), HttpStatus.OK);
  }

  @GetMapping("no-empleados")
  public ResponseEntity<?> noEmpleados() {
    return new ResponseEntity<>(personaService.personasNoEmpleados(), HttpStatus.OK);
  }

  @GetMapping("no-clientes")
  public ResponseEntity<?> noClientes() {
    return new ResponseEntity<>(personaService.personasNoClientes(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Response> guardar(@Valid @RequestBody PersonaDto persona) {
    return new ResponseEntity<>(personaService.guardar(persona), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<Response> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaDto persona) {
    Response response = personaService.modificar(id, persona);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<PersonaDto> obtener(@PathVariable Integer id) {
    PersonaDto personaSave = personaService.obtener(id);
    return new ResponseEntity<>(personaSave, HttpStatus.OK);
  }

  @GetMapping("searchPersona")
  public ResponseEntity<Map<String, Object>> searchPersona(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) String documento,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size) {

    nombre = (nombre != null && !nombre.isBlank()) ? nombre.trim() : null;
    documento = (documento != null && !documento.isBlank()) ? documento.trim() : null;

    Pageable paging = PageRequest.of(page, size);

    Page<Persona> personaPage = personaService.searchPersona(documento, nombre, paging);

    List<PersonaDto> personasDto = personaPage.getContent().stream()
            .map(personaMapper::mapToPersonaDto).toList();

    Map<String, Object> response = Map.of(
            "personas", personasDto,
            "currentPage", personaPage.getNumber(),
            "pageSize", personaPage.getSize(),
            "totalItems", personaPage.getTotalElements(),
            "totalPages", personaPage.getTotalPages(),
            "empty", personaPage.isEmpty()
    );

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
