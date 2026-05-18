package com.svc.ventas.service;

import com.svc.ventas.message.request.AutenticacionRequest;
import com.svc.ventas.message.response.AutenticacionResponse;

public interface IAutenticacionService {

    AutenticacionResponse signIn(AutenticacionRequest signInRequest);

    AutenticacionResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException;
}
