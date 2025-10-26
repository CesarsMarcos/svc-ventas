package com.svc.ventas.controller.compras;

import jakarta.validation.Valid;

import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.service.IProveedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proveedores/")
public class ProveedorController {

	private final IProveedorService proveedorService;

	@GetMapping
	public ResponseEntity<?> proveedores() {
		return new ResponseEntity<>(proveedorService.proveedores(), HttpStatus.OK);
	}

	@GetMapping("list")
	public ResponseEntity<?> proveedoresSelected() {
		return new ResponseEntity<>(proveedorService.proveedoresListSelected(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ProveedorDto proveedorDto) {
		return new ResponseEntity<>(proveedorService.registrar(proveedorDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody ProveedorDto proveedorDto) {
		return new ResponseEntity<>(proveedorService.modificar(id, proveedorDto), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable int id) {
		return new ResponseEntity<>(proveedorService.obtener(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id) {
		proveedorService.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
