package com.svc.ventas.clients;

import com.svc.ventas.message.response.EmpresaSunatResponse;
import com.svc.ventas.message.response.PersonaReniecResponse;
import com.svc.ventas.util.Constantes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = Constantes.SERVICIO_EXTERNO_NAME_CLIENT, url = Constantes.BASE_URL_SERVICIO_EXTERNO)
public interface ReniecClient {

    @GetMapping(Constantes.SUNAT_PATH)
    EmpresaSunatResponse getInfoSunat(@RequestParam("numero") String numero,
                                      @RequestHeader("Authorization") String token);

    @GetMapping(Constantes.RENIEC_PATH)
    PersonaReniecResponse getInfoReniec(@RequestParam("numero") String numero,
                                        @RequestHeader("Authorization") String token);
}
