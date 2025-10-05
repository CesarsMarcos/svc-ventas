package com.svc.ventas.controller.almacen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svc.ventas.models.mapstruct.dto.ProductoGetDTO;
import jakarta.validation.Valid;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.models.mapstruct.dto.ProductoPostDTO;
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
		List<ProductoGetDTO> productos = articuloService.lista();
		if(productos.isEmpty()){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody ProductoPostDTO articuloDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(articuloService.agregar(articuloDto));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody ProductoPostDTO articuloDto) {
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
	public ResponseEntity<?> searchProducto(@RequestParam(required = false) String nombre,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "5") int size){

		Pageable pageable = PageRequest.of(page, size);

		Page<ProductoSearchResponse> pageProductos = articuloService
				.searchProductoPorNombre(nombre,pageable);

		Map<String, Object> response = new HashMap<>();

		response.put("productos", pageProductos.getContent());
		response.put("currentPage", pageProductos.getNumber());
		response.put("totalItems", pageProductos.getTotalElements());
		response.put("totalPages", pageProductos.getTotalPages());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@GetMapping("buscar")
	public ResponseEntity<?> buscarProducto(@RequestParam("termino") String termino) {
		return new ResponseEntity<>(articuloService.buscarPorNombreOCodigo(termino),HttpStatus.OK);}

}
