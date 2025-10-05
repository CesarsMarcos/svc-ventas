package com.svc.ventas.controller.mantenimiento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.TipoPersona;
import com.svc.ventas.service.ITIpoPersonaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tipoPersonas")
public class TipoPersonaController {

	private final ITIpoPersonaService tipoService;

	@GetMapping
	public ResponseEntity<?> tipos() {
		return  ResponseEntity
				.ok().body(tipoService.lista());
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable("id") int id) {
		return new ResponseEntity<>(tipoService.obtener(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> crear(@RequestBody TipoPersona tipo) {
		Response response = tipoService.agregar(tipo);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> editar(@PathVariable("id") int id, @RequestBody TipoPersona tipo) throws Exception {
		Response response = tipoService.modificar(id, tipo);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
		tipoService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
