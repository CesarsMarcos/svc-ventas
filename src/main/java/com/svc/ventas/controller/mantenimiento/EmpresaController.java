package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.models.mapstruct.dto.EmpresaDto;
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
  public ResponseEntity listar(){
    return ResponseEntity.ok(empresaService.listar());
  }

  @PostMapping
  public ResponseEntity guardar(@RequestBody EmpresaDto empresa){
      return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.guardar(empresa));
  }

}
