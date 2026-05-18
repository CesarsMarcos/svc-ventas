package com.svc.ventas.controller.compras;

import com.svc.ventas.message.request.CompraRequest;
import com.svc.ventas.models.mapstruct.dto.EnumDto;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.service.ICompraService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/compras/")
public class CompraController {

  private final ICompraService compraService;

  @GetMapping("searchCompras")
  public ResponseEntity<?> searchCompras(@RequestParam(required = false) String ruc,
                                         @RequestParam(required = false) String proveedor,
                                         @RequestParam(required = false) Long documento,
                                         @RequestParam(required = false) LocalDate inicio,
                                         @RequestParam(required = false) LocalDate fin,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "15") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idCompra"));
    return new ResponseEntity<>(compraService.searchCompras(ruc, proveedor, documento, inicio,
            fin, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> registrar(@Valid @RequestBody CompraRequest compra) {
    return new ResponseEntity<>(compraService.registrar(compra), HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(compraService.details(id), HttpStatus.OK);
  }

  @GetMapping("searchProductoCompraV2")
  public ResponseEntity<?> searchProductosParaCompra(@RequestParam(required = false) String filtro) {
    return new ResponseEntity<>(compraService.searchProductosParaCompra(filtro), HttpStatus.OK);
  }

  @GetMapping("searchProductoCompraV3")
  public ResponseEntity<Map<String, Object>> searchProductosParaCompraPage(
          @RequestParam(required = false) String nombre,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {

    Map<String, Object> response = compraService.searchProductosParaCompraPage(nombre, page, size);
    return ResponseEntity.ok(response);
  }

  @GetMapping("presentacionesPorProducto")
  public ResponseEntity<?> presentacionesPorIdProducto(@RequestParam Long idProducto) {
    return new ResponseEntity<>(compraService.presentacionesPorIdProducto(idProducto), HttpStatus.OK);
  }

  @GetMapping("tipoPagoCompras")
  public ResponseEntity<List<EnumDto>> tipoPagoCompras() {
    return ResponseEntity.ok(compraService.tipoPagoCompra());
  }

}
