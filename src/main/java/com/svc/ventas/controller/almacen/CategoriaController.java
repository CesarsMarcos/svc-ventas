package com.svc.ventas.controller.almacen;

import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.service.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categorias/")
public class CategoriaController {

  private final ICategoriaService categoriaService;

  @GetMapping("search")
  @PreAuthorize("hasAuthority('VER_CATEGORIAS')")
  public ResponseEntity<Map<String, Object>> searchCategorias(@RequestParam(required = false) String nombre,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "15") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idCategoria"));
    Map<String, Object> categorias = categoriaService.searchCategorias(nombre, pageable);

    return ResponseEntity .ok(categorias);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('VER_CATEGORIAS')")
  public ResponseEntity<List<CategoriaDto>> categorias() {
    List<CategoriaDto> categorias = categoriaService.lista();
    if (categorias.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity .ok(categorias);
  }

  @GetMapping("productos")
  @PreAuthorize("hasAuthority('VER_CATEGORIAS')")
  public ResponseEntity<List<CategoriaDto>> categoriasPorProductoStock() {
    List<CategoriaDto> categorias = categoriaService.categoriasPorProductoStock();
    if (categorias.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity
            .ok(categorias);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CREAR_CATEGORIAS')")
  public ResponseEntity<Response> guardar(@RequestBody CategoriaDto categoria) {
    Response response = categoriaService.guardar(categoria);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('VER_CATEGORIAS')")
  public ResponseEntity<?> obtener(@PathVariable int id) {
    return new ResponseEntity<>(categoriaService.obtener(id), HttpStatus.OK);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('EDITAR_CATEGORIAS')")
  public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody CategoriaDto categoria) {
    Response response = categoriaService.modificar(id, categoria);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
