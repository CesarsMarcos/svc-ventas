package com.svc.ventas.message.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaReniecResponse {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
