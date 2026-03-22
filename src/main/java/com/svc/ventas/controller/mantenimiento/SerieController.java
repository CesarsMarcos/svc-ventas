package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.SerieRequest;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;
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
  public ResponseEntity<List<SerieDTO>> listado (){
    List<SerieDTO> series = serieService.series();
    if(series.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(serieService.series());
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody SerieRequest serieRequest){
      return ResponseEntity.status(HttpStatus.CREATED).body(serieService.save(serieRequest));
  }

  @GetMapping("tipoDocumentos")
  public ResponseEntity<?> getByIdDocumentType (){
    return ResponseEntity.ok(serieService.getTipoDocumento());
  }

  @GetMapping("correlativo")
  public ResponseEntity<?> getSeriePorTipoDocumento (@RequestParam Long idTipoDocumento){
    return ResponseEntity.ok(serieService.getSeriePorIdIipoDocumento(idTipoDocumento));
  }

}
