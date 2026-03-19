package com.svc.ventas.service.impl;

import com.svc.ventas.clients.ReniecClient;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.EmpresaSunatResponse;
import com.svc.ventas.message.response.PersonaReniecResponse;
import com.svc.ventas.service.IServicioExterno;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ServicioExternoImpl implements IServicioExterno {

    private final ReniecClient reniecClient;

    @Value("${token.apis.externa}")
    private String tokenApisExterna;

    @Override
    public PersonaReniecResponse getInfoReniec(String dni) {
        return fetchDataFromApi(
                () -> reniecClient.getInfoReniec(dni, tokenApisExterna),
                String.format(Constantes.MENSAJE_ERROR_DNI, dni)
        );
    }

    @Override
    public EmpresaSunatResponse getInfoSunat(String ruc) {
        return fetchDataFromApi(
                () -> reniecClient.getInfoSunat(ruc, tokenApisExterna),
                String.format(Constantes.MENSAJE_ERROR_SUNAT, ruc)
        );
    }

    private <T> T fetchDataFromApi(Supplier<T> apiCall, String notFoundMessage) {
        return Optional.ofNullable(apiCall.get())
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
    }
}
