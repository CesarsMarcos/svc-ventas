package com.svc.ventas.controller.mantenimiento;

import java.util.List;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.PersonaDto;
import com.svc.ventas.service.IPersonaService;
import com.svc.ventas.util.Constantes;
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

	@GetMapping
	public ResponseEntity<?> personas() {
		return new ResponseEntity<>(personaService.personas(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> guardar(@Valid @RequestBody PersonaDto persona) {
		if (personaService.isSaved(persona.getNumDocumento())) {
			return ResponseEntity.status(409).body(Response.builder().mensaje(String.format(Constantes.CONFLICTO_REGISTRO, "Persona")).build());
		}
		return new ResponseEntity<>(personaService.guardar(persona), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Response> modificar(@PathVariable Integer id,@Valid @RequestBody PersonaDto persona) {
		Response response = personaService.modificar(id, persona);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<PersonaDto> obtener(@PathVariable Integer id) {
		PersonaDto personaSave = personaService.obtener(id);
		return new ResponseEntity<>(personaSave, HttpStatus.OK);
	}

}
