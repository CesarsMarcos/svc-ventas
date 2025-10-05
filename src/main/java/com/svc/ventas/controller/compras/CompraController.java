package com.svc.ventas.controller.compras;

import jakarta.validation.Valid;

import com.svc.ventas.models.mapstruct.dto.CompraDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.service.ICompraService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/compras/")
public class CompraController {

	private final ICompraService compraService;

	@GetMapping
	public ResponseEntity<?> compras (@RequestParam Boolean isViewMore){
		return new ResponseEntity<>(compraService.listado(isViewMore), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@Valid @RequestBody CompraDto compra){
		return new ResponseEntity<>(compraService.registrar(compra),HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> details (@PathVariable Long id) {
		return new ResponseEntity<>(compraService.details(id), HttpStatus.OK);
	}
}
