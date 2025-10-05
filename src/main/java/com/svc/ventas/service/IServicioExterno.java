package com.svc.ventas.service;

import com.svc.ventas.message.response.EmpresaSunatResponse;
import com.svc.ventas.message.response.PersonaReniecResponse;

public interface IServicioExterno {

    PersonaReniecResponse getInfoReniec(String dni);

    EmpresaSunatResponse getInfoSunat(String ruc);

}
