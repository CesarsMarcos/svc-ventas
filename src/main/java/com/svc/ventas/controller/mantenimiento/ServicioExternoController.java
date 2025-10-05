package com.svc.ventas.controller.mantenimiento;

import com.svc.ventas.message.response.EmpresaSunatResponse;
import com.svc.ventas.message.response.PersonaReniecResponse;
import com.svc.ventas.service.IServicioExterno;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servicio/externo/")
@CrossOrigin("http://localhost:4200")
public class ServicioExternoController {

    private final IServicioExterno iServicioExterno;

    @GetMapping("reniec/{dni}")
    public ResponseEntity<PersonaReniecResponse> obtenerDataDni(@PathVariable String dni) {
        PersonaReniecResponse reniecResponse = iServicioExterno.getInfoReniec(dni);
        return new ResponseEntity<>(reniecResponse, HttpStatus.OK);
    }

    @GetMapping("sunat/{ruc}")
    public ResponseEntity<EmpresaSunatResponse> obtenerDataRuc(@PathVariable String ruc) {
        EmpresaSunatResponse empresaSunatResponse = iServicioExterno.getInfoSunat(ruc);
        return new ResponseEntity<>(empresaSunatResponse, HttpStatus.OK);
    }

}
