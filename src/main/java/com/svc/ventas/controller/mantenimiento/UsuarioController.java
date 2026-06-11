package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.UsuarioCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import com.svc.ventas.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/usuarios/")
public class UsuarioController {

	private final IUsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<?> usuarios() {
		return new ResponseEntity<>(usuarioService.lista(), HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<Map<String, Object>> search(
					@RequestParam(required = false) String nombre,
					@RequestParam(required = false) String documento,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "3") int size) {
		Map<String, Object> response = usuarioService.usuarios(nombre, documento, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable("id") int id) {
		return new ResponseEntity<UsuarioDto>(usuarioService.obtener(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> crear(@Valid @RequestBody UsuarioCreateRequest usuario) {
		return new ResponseEntity<Response>(usuarioService.agregar(usuario), HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<?> editar(@PathVariable("id") int id, @Valid @RequestBody UsuarioDto usuario) {
		Response response = usuarioService.modificar(id, usuario);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PatchMapping("{id}/update-estado")
	public ResponseEntity<?> modifyEstado(@PathVariable("id") int id) {
		usuarioService.modifyEstado(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
		usuarioService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
