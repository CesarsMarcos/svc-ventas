package com.svc.ventas.controller.almacen;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.message.response.ProductoSearchResponse;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
	public ResponseEntity<?> guardar(@Valid @RequestBody ProductoRequest producto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(articuloService.agregar(producto));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody ProductoRequest producto) {
		return new ResponseEntity<>( articuloService.modificar(id, producto), HttpStatus.OK);

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
	public ResponseEntity<?> searchProductsNameCategoryState(
											@RequestParam(required = false) String nombre,
											@RequestParam(required = false) Integer categoriaId,
											@RequestParam(required = false) Boolean estado,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "10") int size){
		log.info("searchProducto parametros:  nombre {} catergoriaId {} estado {} que se recibe ", nombre, categoriaId, estado);
		Map<String, Object> response = articuloService.searchProductos(nombre, categoriaId, estado, page, size);
		return ResponseEntity.ok(response);
	}

	@GetMapping("search-sales")
	public ResponseEntity<?> searchProductNameCode(@RequestParam(required = false) String termino,
																					@RequestParam(defaultValue = "0") int page,
																					@RequestParam(defaultValue = "5") int size){
		Map<String, Object> response = articuloService.searchProductsSales(termino, page, size);
		return ResponseEntity.ok(response);
	}

	@GetMapping("search-products")
	public ResponseEntity<?> searchProducts(@RequestParam(required = false) String termino){
		List<ProductoSearchResponse> response = articuloService.buscarPorNombreOCodigo(termino);
		return ResponseEntity.ok(response);
	}

}
