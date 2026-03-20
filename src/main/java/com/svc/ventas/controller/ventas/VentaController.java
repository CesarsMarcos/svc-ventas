package com.svc.ventas.controller.ventas;

import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.models.mapstruct.dto.EnumDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseEntity<?> registrar(@Valid @RequestBody VentaRequest ventarRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(ventarRequest));
  }

  @GetMapping("searchVentas")
  public ResponseEntity<Map<String, Object>> searchVentas(@RequestParam(required = false) String nombre,
                                                          @RequestParam(required = false) String documentoCliente,
                                                          @RequestParam(required = false) String documentoVenta,
                                                          @RequestParam(required = false) LocalDate inicio,
                                                          @RequestParam(required = false) LocalDate fin,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "15") int size) {
    Pageable paging = PageRequest.of(page, size);
    Map<String, Object> response = ventaService.searchVenta(nombre, documentoCliente, documentoVenta, inicio, fin, paging);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @GetMapping("{id}")
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
