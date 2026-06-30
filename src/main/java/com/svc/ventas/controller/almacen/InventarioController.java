package com.svc.ventas.controller.almacen;

import com.svc.ventas.message.request.PresentacionUpdateRequest;
import com.svc.ventas.models.mapstruct.dto.ProductoStockPresentacionDto;
import com.svc.ventas.service.IAlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

@RequestMapping("api/inventario/")
public class InventarioController {

  private final IAlmacenService almacenService;

  @GetMapping("search")
  @PreAuthorize("hasAuthority('VER_INVENTARIO')")
  public ResponseEntity<Map<String, Object>> search(
          @RequestParam(required = false) String nombre,
          @RequestParam(required = false) Integer categoriaId,
          @RequestParam(required = false) Boolean estado,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> response = almacenService.buscarProductos(nombre, categoriaId, estado, page, size);
    return ResponseEntity.ok(response);
  }

  @GetMapping("details/{id}")
  @PreAuthorize("hasAuthority('VER_INVENTARIO')")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(almacenService.obtenerDetalle(id), HttpStatus.OK);
  }

  @GetMapping("{id}/presentaciones")
  @PreAuthorize("hasAuthority('VER_INVENTARIO')")
  public ResponseEntity<List<ProductoStockPresentacionDto>> presentaciones(@PathVariable Long id) {
    return new ResponseEntity<>(almacenService.listarPresentaciones(id), HttpStatus.OK);
  }

  @PatchMapping("updatePrecioVenta")
  @PreAuthorize("hasAuthority('AJUSTAR_PRECIOS')")
  public ResponseEntity<Void> updatePrecioVenta(@RequestBody List<PresentacionUpdateRequest> presentaciones) {
    almacenService.actualizarPreciosVenta(presentaciones);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{idProducto}/update-estado")
  @PreAuthorize("hasAuthority('EDITAR_ESTADO_INVENTARIO')")
  public ResponseEntity<Void> updateEstado(@PathVariable Long idProducto) {
    almacenService.actualizarEstado(idProducto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
