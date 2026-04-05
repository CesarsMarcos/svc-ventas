package com.svc.ventas.service.impl;

import com.svc.ventas.message.request.AutenticacionRequest;
import com.svc.ventas.message.response.AutenticacionResponse;
import com.svc.ventas.message.response.MenuResponse;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.service.CustomUserDetailsService;
import com.svc.ventas.service.IAutenticacionService;
import com.svc.ventas.service.IJwtService;
import com.svc.ventas.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements IAutenticacionService {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final IUsuarioService usuarioService;

    @Override
    public AutenticacionResponse signIn(AutenticacionRequest signInRequest) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getUsuario());

        Usuario usuario = usuarioService.getUsuarioPorUserName(signInRequest.getUsuario());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsuario(),signInRequest.getClave()));

        var token = jwtService.generateToken(usuario);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),userDetails);

        List<MenuResponse> menus = usuarioService.getMenusPorUsuario(usuario);

        return AutenticacionResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .menus(menus)
                .build();
    }

    @Override
    public AutenticacionResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException {
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new RuntimeException("Error el token ingresado no es un REFRESH ");
        }
        String user = jwtService.extractUsername(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if(!jwtService.validateToken(refreshToken,userDetails )){
            throw new IllegalAccessException("Error el token no le pertenece a al usuario");
        }

        String newToken = jwtService.generateToken(usuarioService.getUsuarioPorUserName(user));
        return AutenticacionResponse.builder()
                .accessToken(newToken)
                .refreshToken(refreshToken)
                .build();
    }
}
