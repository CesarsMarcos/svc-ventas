package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.SucursalRequest;
import com.svc.ventas.models.mapstruct.dto.SucursalDto;
import jakarta.validation.Valid;
import com.svc.ventas.service.ISucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sucursales/")
public class SucursalController {

	private final ISucursalService sucursalService;

	@GetMapping()
	@PreAuthorize("hasAuthority('VER_SUCURSALES')")
	public ResponseEntity<?> sucursales() {
		List<SucursalDto> sucursales = sucursalService.lista();
		if(sucursales.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity
				.ok(sucursales);
	}

	@GetMapping("roles")
	@PreAuthorize("hasAuthority('LISTA_ROLES_SUCURSALES')")
	public ResponseEntity<?> sucursalesPorRoles() {
		List<SucursalDto> sucursales = sucursalService.listKardex();
		if(sucursales.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity
						.ok(sucursales);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('VER_SUCURSALES')")
	public ResponseEntity<?> obtener(@PathVariable Long id) {
		return new ResponseEntity<> (sucursalService.obtener(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('CREAR_SUCURSALES')")
	public ResponseEntity<?> agregar(@Valid @RequestBody SucursalRequest sucursalRequest) {
		return  ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.agregar(sucursalRequest));
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('EDITAR_SUCURSALES')")
	public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody SucursalDto sucursalDto) {
		return new ResponseEntity<> (sucursalService.modificar(id, sucursalDto), HttpStatus.OK);
	}

}
