package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDto;
import com.svc.ventas.service.ITipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.TipoDocumento;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tipoDocumentos/")
public class TipoDocumentoController {

	private final ITipoDocumentoService tipoService;

	@GetMapping
	public ResponseEntity<?> tipos() {
		return ResponseEntity
				.ok(tipoService.lista());
	}

	@GetMapping("tipo/{tipo}")
	public ResponseEntity<?> listadoPorTipo(@PathVariable Integer tipo) {
		return ResponseEntity
						.ok(tipoService.listaPorTipo(tipo));
	}

	@PostMapping
	public ResponseEntity<Response> guardar(@RequestBody TipoDocumentoDto documento) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(tipoService.agregar(documento));
	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable Integer id) {
		return new ResponseEntity<>(tipoService.obtener(id), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody TipoDocumento documento) {
		return new ResponseEntity<Response>(tipoService.modificar(id, documento), HttpStatus.OK);
	}

}
