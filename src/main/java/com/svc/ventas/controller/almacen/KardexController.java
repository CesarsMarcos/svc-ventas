package com.svc.ventas.controller.almacen;

import com.svc.ventas.message.response.KardexResponse;
import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;
import com.svc.ventas.service.IKardexService;
import com.svc.ventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
  public ResponseEntity<List<KardexResponse>> listarKardexPorFecha(
          @RequestParam(required = false) Long idSucursal,
          @RequestParam(required = false) Long idProducto,
          @RequestParam(required = false) String inicio,
          @RequestParam(required = false) String fin
  ) {
    List<KardexResponse> resumen = kardexService.listarKardexPorFecha(idSucursal, idProducto, inicio, fin);
    return ResponseEntity.ok(resumen);
  }

}
