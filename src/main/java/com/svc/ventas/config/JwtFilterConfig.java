package com.svc.ventas.config;

import com.svc.ventas.service.CustomUserDetailsService;
import com.svc.ventas.service.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilterConfig extends OncePerRequestFilter {

    private static final int NUM_SIETE = 7;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final IJwtService jwtService;
    private final CustomUserDetailsService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String tokenExtraidoHeader = request.getHeader(AUTHORIZATION);

        final String tokenLimpio;
        final String userEmail;

        if (!StringUtils.hasText(tokenExtraidoHeader)
                || !StringUtils.startsWithIgnoreCase(tokenExtraidoHeader, BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        tokenLimpio = tokenExtraidoHeader.substring(NUM_SIETE);
        try {
            userEmail = jwtService.extractUsername(tokenLimpio);

            if (Objects.nonNull(userEmail)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UserDetails userDetails = usuarioService.loadUserByUsername(userEmail);
                if (jwtService.validateToken(tokenLimpio, userDetails) &&
                        !jwtService.isRefreshToken(tokenLimpio)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException ex){
            request.setAttribute("exception", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.getRequestDispatcher("/error").forward(request, response);
        }

    }
}
