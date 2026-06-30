package com.svc.ventas.controller.mantenimiento;

import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.ProductoRequest;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/articulos/")
public class ProductoController {

  private final IProductoService articuloService;

  @GetMapping
  @PreAuthorize("hasAuthority('VER_PRODUCTOS')")
  public ResponseEntity<List<ProductoDTO>> articulos() {
    List<ProductoDTO> productos = articuloService.lista();
    if (productos.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CREAR_PRODUCTOS')")
  public ResponseEntity<?> guardar(@Valid @RequestBody ProductoRequest producto) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(articuloService.agregar(producto));
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('EDITAR_PRODUCTOS')")
  public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody ProductoRequest producto) {
    return new ResponseEntity<>(articuloService.modificar(id, producto), HttpStatus.OK);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('VER_PRODUCTOS')")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    return new ResponseEntity<>(articuloService.obtener(id), HttpStatus.OK);
  }

  @GetMapping("details/{id}")
  @PreAuthorize("hasAuthority('VER_PRODUCTOS')")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(articuloService.details(id), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('ELIMINAR_PRODUCTOS')")
  public void eliminar(@PathVariable Long id) {
    articuloService.eliminar(id);
  }

  @GetMapping("search")
  @PreAuthorize("hasAuthority('VER_PRODUCTOS')")
  public ResponseEntity<Map<String, Object>> searchProductsNameCategoryState(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) Integer categoriaId,
          @RequestParam(required = false) Boolean estado,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> response = articuloService.searchProductos(nombre, categoriaId, estado, page, size);
    return ResponseEntity.ok(response);
  }

  @PutMapping("{idProducto}/update-estado")
  @PreAuthorize("hasAuthority('EDITAR_ESTADO_PRODUCTOS')")
  public ResponseEntity<Void> updateEstado(@PathVariable Long idProducto) {
    articuloService.updateEstado(idProducto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
