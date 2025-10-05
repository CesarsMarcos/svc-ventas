package com.svc.ventas.message.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacionRequest {
    private String usuario;
    private String clave;
}
