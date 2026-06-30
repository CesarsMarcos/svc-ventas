package com.svc.ventas.controller.ventas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.svc.ventas.message.request.ClienteCreateParaVentaRequest;
import com.svc.ventas.message.request.ClienteCreateRequest;
import com.svc.ventas.models.mapstruct.dto.ClienteGetDto;
import com.svc.ventas.service.UbigeoService;
import jakarta.validation.Valid;
import com.svc.ventas.models.mapstruct.dto.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.mappers.ClienteMapper;
import com.svc.ventas.service.IClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clientes/")
public class ClienteController {

  private final IClienteService clienteService;

  private final ClienteMapper clienteMapper;

  private final UbigeoService ubigeoService;

  @GetMapping
  public ResponseEntity<?> clientes() {
    return new ResponseEntity<>(clienteService.clientes(), HttpStatus.OK);
  }

  @GetMapping("ventas")
  public ResponseEntity<?> clientesParaVenta() {
    return new ResponseEntity<>(clienteService.clientesParaVenta(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> guardar(@RequestBody @Valid ClienteCreateRequest cliente) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(clienteService.agregar(cliente));
  }

  @PostMapping("registroVentas")
  public ResponseEntity<?> guardarParaVentas(@RequestBody @Valid ClienteCreateParaVentaRequest cliente) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(clienteService.agregarParaVenta(cliente));
  }

  @PatchMapping("{idCliente}")
  public ResponseEntity<?> update(@RequestBody @Valid ClienteDto cliente, @PathVariable Integer idCliente) {
    return ResponseEntity.ok(clienteService.modificar(idCliente, cliente));
  }

  @GetMapping("{id}")
  public ResponseEntity<?> obtener(@PathVariable Integer id) {
    return new ResponseEntity<ClienteDto>(clienteService.obtener(id), HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<Map<String, Object>> searchCliente(
          @RequestParam(required = false) String termino,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size) {

    Pageable paging = PageRequest.of(page, size);

    Page<Cliente> pageCliente = clienteService.searchCliente(termino, paging);

    List<ClienteGetDto> clientesDto = pageCliente.getContent().stream()
            .map(clienteMapper::mapClienteGet).collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("clientes", clientesDto);
    response.put("currentPage", pageCliente.getNumber());
    response.put("totalItems", pageCliente.getTotalElements());
    response.put("totalPages", pageCliente.getTotalPages());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("search-ventas-v2")
  public ResponseEntity<?> clientesParaVentaV2(@RequestParam(required = false) String termino,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idCliente"));
    return new ResponseEntity<>(clienteService.searchClientesParaVenta(termino, pageable), HttpStatus.OK);
  }

  @GetMapping("cliente-final")
  public ResponseEntity<?> getClienteFinal() {
    return new ResponseEntity<>(clienteService.getClienteFinal(), HttpStatus.OK);
  }

  @GetMapping("departamentos")
  public List<String> getDepartamentos() {
    return ubigeoService.getDepartamentos();
  }

  @GetMapping("provincias")
  public List<Map<String, String>> getProvincias(@RequestParam("departamento") String departamento) {
    return ubigeoService.provinciasByDepartamento(departamento);
  }

  @GetMapping("distritos")
  public List<Map<String, String>> getDistritos(@RequestParam("provincia") String provincia) {
    return ubigeoService.distritosByProvincia(provincia);
  }

}
