package com.svc.ventas.controller.almacen;

import java.util.List;

import com.svc.ventas.service.IUnidadMedidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.UnidadMedida;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/unidadMedidas/")
public class UnidadMedidaController {

	private final IUnidadMedidaService unidadService;

	@GetMapping
	public ResponseEntity<List<UnidadMedida>> unidades() {
		List<UnidadMedida> lista = unidadService.unidades();
		if(lista.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> guardar(@RequestBody UnidadMedida unidad) {
		Response response = unidadService.guardar(unidad);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody UnidadMedida unidad) {
		Response response = unidadService.modificar(id, unidad);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable int id) {
		return new ResponseEntity<>(unidadService.obtener(id), HttpStatus.OK);
	}

}
