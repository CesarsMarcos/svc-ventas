package com.svc.ventas.controller.almacen;

import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.KardexResumenDTO;
import com.svc.ventas.service.IKardexService;
import com.svc.ventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/kardex")
@RequiredArgsConstructor
public class KardexController {

  private final IKardexService kardexService;
  private final IProductoService productoService;

  @GetMapping("/{idProducto}")
  public ResponseEntity<List<KardexDetalleDTO>> obtenerKardex(@PathVariable Long idProducto) {

    productoService.obtener(idProducto);

    List<KardexDetalleDTO> kardex = kardexService.obtenerKardexPorProducto(idProducto);
    return ResponseEntity.ok(kardex);
  }

  @GetMapping("/resumen")
  public ResponseEntity<List<KardexResumenDTO>> listarKardexPorFecha(
          @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate inicio,
          @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fin
  ) {
    LocalDate fechaInicio = inicio != null ? inicio : LocalDate.now();
    LocalDate fechaFin = fin != null ? fin : LocalDate.now();

    List<KardexResumenDTO> resumen = kardexService.listarKardexPorFecha(fechaInicio, fechaFin);
    return ResponseEntity.ok(resumen);
  }

}
