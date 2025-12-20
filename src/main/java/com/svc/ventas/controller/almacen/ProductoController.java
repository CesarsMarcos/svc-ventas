package com.svc.ventas.controller.almacen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/articulos/")
public class ProductoController {

	private final IProductoService articuloService;

	@GetMapping
	public ResponseEntity<?> articulos() {
		List<ProductoDTO> productos = articuloService.lista();
		if(productos.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ProductoDTO articuloDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(articuloService.agregar(articuloDto));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody ProductoDTO articuloDto) {
		return new ResponseEntity<>( articuloService.modificar(id, articuloDto), HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable Long id) {
		return new ResponseEntity<>(articuloService.obtener(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public void eliminar (@PathVariable Long id) {
		articuloService.eliminar(id);
	}

	@GetMapping("search")
	public ResponseEntity<?> searchProducto(
											@RequestParam(required = false) String nombre,
											@RequestParam(required = false) Long catergoriaId,
											@RequestParam(required = false) Boolean estado,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "10") int size){
		Map<String, Object> response = articuloService.searchProductos(nombre, catergoriaId, estado, page, size);
		return ResponseEntity.ok(response);
	}

	@GetMapping("search-sales")
	public ResponseEntity<?> searchProductSales(@RequestParam(required = false) String nombre,
																					@RequestParam(defaultValue = "0") int page,
																					@RequestParam(defaultValue = "5") int size){
		Map<String, Object> response = articuloService.searchProductsSales(nombre, page, size);
		return ResponseEntity.ok(response);
	}

}
