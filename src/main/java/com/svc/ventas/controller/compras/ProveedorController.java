package com.svc.ventas.controller.compras;

import jakarta.validation.Valid;

import com.svc.ventas.models.mapstruct.dto.ProveedorDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.service.IProveedorService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proveedores/")
public class ProveedorController {

	private final IProveedorService proveedorService;

	@GetMapping("search")
	public ResponseEntity<Map<String, Object>> searchProveedores(
					@RequestParam(required = false) String razonSocial,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		Map<String, Object> response = proveedorService.searchProveedor(razonSocial, paging);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

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
	public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody ProveedorDto proveedorDto) {
		return new ResponseEntity<>(proveedorService.modificar(id, proveedorDto), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable Long id) {
		return new ResponseEntity<>(proveedorService.obtener(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		proveedorService.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("searchReniec")
	public ResponseEntity<?> search(@RequestParam String tipoDocumento,
																	@RequestParam String numDocumento) {
		return ResponseEntity.ok(proveedorService.searchProveedor(tipoDocumento, numDocumento));
	}

}
