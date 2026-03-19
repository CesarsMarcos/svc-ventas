package com.svc.ventas.controller.almacen;

import com.svc.ventas.service.IAlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor

@RequestMapping("api/inventario/")
public class InventarioController {

  private final IAlmacenService almacenService;

  /**
   * Listado del inventario principal
   * @param nombre
   * @param categoriaId
   * @param estado
   * @param page
   * @param size
   * @return Map<String, Object>
   */
  @GetMapping("search")
  public ResponseEntity<Map<String, Object>> search(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) Integer categoriaId,
          @RequestParam(required = false) Boolean estado,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> response = almacenService.searchProductos(nombre, categoriaId, estado, page, size);
    return ResponseEntity.ok(response);
  }

  @GetMapping("searchProductosVentasPOS")
  public ResponseEntity<Map<String, Object>> searchProductoVenta(
          @RequestParam(required = false) String codigo,
          @RequestParam(required = false) String nombre) {
    Map<String, Object> response = almacenService.searchProductosVenta(codigo, nombre);
    return ResponseEntity.ok(response);
  }

  @GetMapping("details/{id}")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(almacenService.details(id), HttpStatus.OK);
  }

  @PutMapping("{idProductoStock}")
  public ResponseEntity<Void> updatePrecioVenta (Long idProductoStock, @RequestParam BigDecimal precioVenta){
    almacenService.updatePrecioVenta(idProductoStock, precioVenta);
    return ResponseEntity.ok().build();
  }

}
