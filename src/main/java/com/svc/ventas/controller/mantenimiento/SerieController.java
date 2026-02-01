package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.service.ISerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/series/")
public class SerieController {

  private final ISerieService serieService;

  @GetMapping
  public ResponseEntity<List<Serie>> listado (){
    List<Serie> series = serieService.series();
    if(series.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(serieService.series());
  }

  @PostMapping
  public ResponseEntity<?> save (@RequestBody Serie serie){
      return ResponseEntity.status(HttpStatus.CREATED).body(serieService.save(serie));
  }

  @GetMapping("documentType/{idSucursal}")
  public ResponseEntity<?> getByIdDocumentType (@PathVariable Long idSucursal, @RequestParam TipoDocumento tipoDocumento){
    return ResponseEntity.ok(serieService.getByIdDocumentType(idSucursal, tipoDocumento));
  }

}
