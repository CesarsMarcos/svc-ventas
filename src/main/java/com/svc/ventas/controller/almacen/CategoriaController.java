package com.svc.ventas.controller.almacen;

import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.service.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categorias/")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<List<CategoriaDto>> categorias() {
		List<CategoriaDto> categorias = categoriaService.lista();
        if (categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity
                .ok(categorias);
    }

    @GetMapping("productos")
    public ResponseEntity<List<CategoriaDto>> categoriasPorProductoStock(){
        List<CategoriaDto> categorias = categoriaService.categoriasPorProductoStock();
        if (categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity
                .ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Response> guardar(@RequestBody CategoriaDto categoria) {
        Response response = categoriaService.guardar(categoria);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtener(@PathVariable int id) {
        return new ResponseEntity<>(categoriaService.obtener(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody CategoriaDto categoria) {
        Response response = categoriaService.modificar(id, categoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
