package com.svc.ventas.controller;

import java.util.List;

import com.svc.ventas.models.mapstruct.dto.EmpresaPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.service.IEmpresaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/global/")
public class GlobalController {

	private final IEmpresaService globalService;

	@GetMapping
	public ResponseEntity<List<Empresa>> listar() {
		List<Empresa> global = globalService.listar();
		return new ResponseEntity<List<Empresa>>(global, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> guardar(@RequestBody EmpresaPostDto global) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(globalService.guardar(global));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody EmpresaPostDto global) {
		return new ResponseEntity<Response>(globalService.modificar(id,global), HttpStatus.OK);
	}

}
