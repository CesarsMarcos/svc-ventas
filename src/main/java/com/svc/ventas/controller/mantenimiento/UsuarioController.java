package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.UsuarioCreateRequest;
import jakarta.validation.Valid;
import com.svc.ventas.util.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import com.svc.ventas.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/usuarios/")
public class UsuarioController {

	private final IUsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<?> usuarios() {
		return new ResponseEntity<>(usuarioService.lista(), HttpStatus.OK);
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

	@DeleteMapping()
	public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
		usuarioService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
