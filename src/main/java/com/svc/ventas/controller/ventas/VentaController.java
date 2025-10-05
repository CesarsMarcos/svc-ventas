package com.svc.ventas.controller.ventas;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.models.mapstruct.dto.VentaDto;
import com.svc.ventas.service.IVentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ventas/")
public class VentaController {

	private final IVentaService ventaService;

	@PostMapping
	public ResponseEntity<?> registrar (@Valid @RequestBody VentaDto ventaDto) {
		return  ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(ventaDto));
	}

	@GetMapping
	public ResponseEntity<?> ventas (@RequestParam Boolean isViewMore) {
		return ResponseEntity.ok(ventaService.listado(isViewMore));
	}

	@GetMapping("{id}")
	public ResponseEntity<?> details (@PathVariable Long id) {
		return new ResponseEntity<>(ventaService.details(id), HttpStatus.OK);
	}

	@GetMapping("cliente")
	public ResponseEntity<?> searchVentas(@RequestParam String dni/*, @RequestParam String fecha*/) {
		return ResponseEntity.ok(ventaService.listadoVentasPorCliente(dni/*,fecha*/));
	}

}
