package com.svc.ventas.service;

import com.svc.ventas.models.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {
    String extractUsername(String token);
    String generateToken(Usuario usuario);
    boolean validateToken(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isRefreshToken(String token);

}
