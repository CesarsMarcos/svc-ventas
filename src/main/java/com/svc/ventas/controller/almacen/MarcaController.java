package com.svc.ventas.controller.almacen;

import com.svc.ventas.models.mapstruct.dto.MarcaGetDto;
import com.svc.ventas.models.mapstruct.dto.MarcaPostDto;
import com.svc.ventas.service.IMarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.svc.ventas.message.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/marcas/")
public class MarcaController {

	private final IMarcaService marcaService;

	@GetMapping
	public ResponseEntity<List<MarcaGetDto>> listar() {
		List<MarcaGetDto> marcas = marcaService.lista();
		if (marcas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity
				.ok(marcaService.lista());
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable int id) {
		MarcaGetDto marca = marcaService.obtener(id);
		return new ResponseEntity<MarcaGetDto>(marca, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> guardar(@RequestBody MarcaPostDto marca) {
		Response response = marcaService.guardar(marca);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<Response> modificar(@PathVariable int id, @RequestBody MarcaPostDto marca) {
		Response response = marcaService.modificar(id, marca);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

}
