package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.request.EmpresaPostRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import com.svc.ventas.service.IEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/empresas/")
public class EmpresaController {

  private final IEmpresaService empresaService;

  @GetMapping
  public ResponseEntity<?> listar(){
    return ResponseEntity.ok(empresaService.listar());
  }

  @PatchMapping("modificarAplicaImpuesito")
  public ResponseEntity<?> cambiarAplicaImpuesto(
          @RequestParam("idEmpresa") Integer idEmpresa,
          @RequestParam("aplica") Boolean aplica){
    Response response = empresaService.cambiarAplicacionImpuesto(idEmpresa, aplica);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<?> guardar(@RequestBody EmpresaPostRequest empresa){
      return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.guardar(empresa));
  }

  @GetMapping("{id}")
  public ResponseEntity<EmpresaGetDto> obtener(@PathVariable Integer id) {
    EmpresaGetDto empresaDto = empresaService.obtener(id);
    return new ResponseEntity<>(empresaDto, HttpStatus.OK);
  }

}
