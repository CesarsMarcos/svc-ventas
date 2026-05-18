package com.svc.ventas.controller.almacen;

import com.svc.ventas.message.request.PresentacionUpdateRequest;
import com.svc.ventas.models.mapstruct.dto.ProductoStockPresentacionDto;
import com.svc.ventas.service.IAlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

  @GetMapping("details/{id}")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(almacenService.details(id), HttpStatus.OK);
  }

  @GetMapping("{id}/presentaciones")
  public ResponseEntity<List<ProductoStockPresentacionDto>> presentaciones(@PathVariable Long id) {
    return new ResponseEntity<>(almacenService.presentacionesPorProductoStock(id), HttpStatus.OK);
  }

  @PatchMapping("updatePrecioVenta")
  public ResponseEntity<Void> updatePrecioVenta(@RequestBody List<PresentacionUpdateRequest> presentaciones) {
    almacenService.updatePrecioVentaPresentaciones(presentaciones);
    return ResponseEntity.ok().build();
  }

}
