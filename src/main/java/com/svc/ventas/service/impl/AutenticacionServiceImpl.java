package com.svc.ventas.service.impl;

import com.svc.ventas.message.request.AutenticacionRequest;
import com.svc.ventas.message.response.AutenticacionResponse;
import com.svc.ventas.models.dao.UsuarioRepo;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.service.IAutenticacionService;
import com.svc.ventas.service.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements IAutenticacionService {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final UsuarioRepo usuarioRepo;
    @Override
    public AutenticacionResponse signIn(AutenticacionRequest signInRequest) {
        var user = usuarioRepo.findByUsuario(signInRequest.getUsuario()).orElseThrow(
                ()-> new UsernameNotFoundException("Error usuario no encontrado!!"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsuario(),signInRequest.getClave()));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        return AutenticacionResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AutenticacionResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException {
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new RuntimeException("Error el token ingresado no es un REFRESH ");
        }
        String user = jwtService.extractUsername(refreshToken);

        Usuario usuario = usuarioRepo.findByUsuario(user).orElseThrow(
                ()-> new UsernameNotFoundException("Error usuario no encontrado"));
        if(!jwtService.validateToken(refreshToken, usuario)){
            throw new IllegalAccessException("Error el token no le pertenece a al usuario");
        }
        String newToken = jwtService.generateToken(usuario);
        return AutenticacionResponse.builder()
                .accessToken(newToken)
                .refreshToken(refreshToken)
                .build();
    }
}
