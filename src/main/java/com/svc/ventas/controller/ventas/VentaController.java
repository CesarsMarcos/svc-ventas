package com.svc.ventas.controller.ventas;

import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.EnumDto;
import com.svc.ventas.service.IPrintDocumentoService;
import com.svc.ventas.service.documentoStrategy.documento.DocumentoPdfFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.service.IVentaService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ventas/")
public class VentaController {

  private final IVentaService ventaService;

  private final IPrintDocumentoService iPrintDocumentoService;

  private final DocumentoPdfFactory documentoPdfFactory;

  @PostMapping
  @PreAuthorize("hasAuthority('REGISTRAR_VENTA')")
  public ResponseEntity<?> registrar(@Valid @RequestBody VentaRequest ventarRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(ventarRequest));
  }

  @GetMapping("searchVentas")
  @PreAuthorize("hasAuthority('VER_VENTAS')")
  public ResponseEntity<Map<String, Object>> searchVentas(@RequestParam(required = false) String nombre,
                                                          @RequestParam(required = false) String documentoCliente,
                                                          @RequestParam(required = false) String documentoVenta,
                                                          @RequestParam(required = false) LocalDate inicio,
                                                          @RequestParam(required = false) LocalDate fin,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "15") int size) {
    Map<String, Object> response = ventaService.searchVenta(nombre, documentoCliente, documentoVenta, inicio, fin, page, size );
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @GetMapping("searchProductosVentas")
  @PreAuthorize("hasAuthority('VER_VENTAS')")
  public ResponseEntity<?> listProductosPresentacionesParaVenta(
          @RequestParam(required = false) String filtro,
          @RequestParam(required = false) Long idCategoria) {
    return new ResponseEntity<>(ventaService.buscarPorNombreOCodigoPresentacionesParaVenta(filtro), HttpStatus.OK);
  }

  @GetMapping("searchProductosVentasPos")
  @PreAuthorize("hasAuthority('VER_VENTAS')")
  public ResponseEntity<Map<String, Object>> searchProductoVenta(
          @RequestParam(required = false) String filtro,
          @RequestParam(required = false) Long categoriaId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "15") int size) {
    Map<String, Object> response = ventaService.searchProductosVentaPos(filtro, categoriaId, page, size);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/printDocumento/{id}/pdf")
  @PreAuthorize("hasAuthority('VER_VENTAS')")
  public ResponseEntity<byte[]> generarPdf(@PathVariable Long id) {

    DetalleImpresionDto venta = iPrintDocumentoService.detailsImpresion(id);

    byte[]  pdf = ventaService.generarPdf(venta, TipoDocumento.from(venta.getTipoDocumento()));

    return ResponseEntity.ok()
            .header("Content-Disposition", "inline; filename=doc.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('VER_VENTAS')")
  public ResponseEntity<?> details(@PathVariable Long id) {
    return new ResponseEntity<>(ventaService.details(id), HttpStatus.OK);
  }

  @GetMapping("cliente")
  public ResponseEntity<?> searchVentas(@RequestParam String dni/*, @RequestParam String fecha*/) {
    return ResponseEntity.ok(ventaService.listadoVentasPorCliente(dni/*,fecha*/));
  }

  @GetMapping("tipoPago")
  public ResponseEntity<List<EnumDto>> tipoPagoEnums() {
    return ResponseEntity.ok(ventaService.tipoPago());
  }

  @GetMapping("tipoDocumentoPersona")
  public ResponseEntity<List<EnumDto>> tipoDocumentoPersonaEnums() {
    return ResponseEntity.ok(ventaService.tipoDocumentoPersona());
  }

}
