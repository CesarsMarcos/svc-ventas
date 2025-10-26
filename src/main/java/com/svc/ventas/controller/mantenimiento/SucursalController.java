package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import jakarta.validation.Valid;
import com.svc.ventas.service.ISucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sucursales/")
public class SucursalController {

	private final ISucursalService sucursalService;

	@GetMapping()
	public ResponseEntity<?> sucursales() {
		List<SucursalDto> sucursales = sucursalService.lista();
		if(sucursales.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity
				.ok(sucursales);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable int id) {
		return new ResponseEntity<> (sucursalService.obtener(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> agregar(@Valid @RequestBody SucursalDto sucursalDto) {
		return  ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.agregar(sucursalDto));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody SucursalDto sucursalDto) {
		return new ResponseEntity<> (sucursalService.modificar(id, sucursalDto), HttpStatus.OK);
	}

}
