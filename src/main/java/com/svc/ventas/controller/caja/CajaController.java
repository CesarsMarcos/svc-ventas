package com.svc.ventas.controller.caja;

import com.svc.ventas.message.request.CajaAperturaRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.mapstruct.dto.CajaDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.CajaMovimientosDTO;
import com.svc.ventas.service.ICajaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/caja/")
public class CajaController {

    private final ICajaService cajaService;

    @PostMapping("aperturar")
    @PreAuthorize("hasAuthority('APERTURAR_CAJA')")
    public ResponseEntity<Response> aperturarCaja (@Valid @RequestBody CajaAperturaRequest caja){
        return new ResponseEntity<>(cajaService.aperturaCaja(caja), HttpStatus.OK);
    }

    @PutMapping("cerrar")
    @PreAuthorize("hasAuthority('CERRAR_CAJA')")
    public ResponseEntity<Response> cerrarCaja (@RequestParam Long idCaja){
        return new ResponseEntity<>(cajaService.cerrarCaja(idCaja), HttpStatus.OK);
    }

    @PutMapping("movimientos")
    @PreAuthorize("hasAuthority('VER_CAJA')")
    public ResponseEntity<Response> movimiento (@RequestParam Long idCaja,@RequestBody CajaMovimientosDTO movimiento ){
        return new ResponseEntity<>(cajaService.agregarMovimiento(idCaja, movimiento), HttpStatus.OK);
    }

   @GetMapping
   @PreAuthorize("hasAuthority('VER_CAJA')")
   public ResponseEntity<CajaDetalleDTO> getCajaPorUsuario (){
        return new ResponseEntity<>(cajaService.findByFechaAndUsuario(), HttpStatus.OK);
   }

   @GetMapping("reporteCierre")
   @PreAuthorize("hasAuthority('VER_CAJA')")
   public ResponseEntity<?> reporteCierreCaja (@RequestParam Long idCaja){
       return new ResponseEntity<>(cajaService.calcularCierreCaja(idCaja), HttpStatus.OK);
   }
}
