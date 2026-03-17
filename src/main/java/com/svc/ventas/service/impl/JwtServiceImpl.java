package com.svc.ventas.service.impl;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.service.IJwtService;
import com.svc.ventas.util.Constantes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${key.signature}")
    private String keySignature;
    @Override
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(addClaim(usuario))
                .setSubject(usuario.getUsuario())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .claim(Constantes.TYPE_TOKEN, Constantes.ACCESS)
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1200000))
                .claim(Constantes.TYPE_TOKEN, Constantes.REFRESH)
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        String tokenType = claims.get("type",String.class);
        return Constantes.REFRESH.equalsIgnoreCase(tokenType);
    }

    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(keySignature);
        return Keys.hmacShaKeyFor(key);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(extractAllClaims(token));
    }
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Map<String, Object> addClaim(Usuario usuario){
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constantes.CLAIM_USER,usuario.getEmpleado().getPersona().getNombre() + " "
                + usuario.getEmpleado().getPersona().getApePaterno() + " "
                + usuario.getEmpleado().getPersona().getApeMaterno()  );
        claims.put(Constantes.CLAIM_ROL,usuario.getRoles());
        return claims;
    }
}
