package com.svc.ventas.service;

import com.svc.ventas.message.request.AutenticacionRequest;
import com.svc.ventas.message.response.AutenticacionResponse;

public interface IAutenticacionService {

    AutenticacionResponse autenticar(AutenticacionRequest signInRequest);

    AutenticacionResponse refrescarToken(String refreshToken) throws IllegalAccessException;
}
