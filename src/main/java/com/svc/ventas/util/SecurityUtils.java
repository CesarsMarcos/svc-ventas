package com.svc.ventas.util;

import com.svc.ventas.models.mapstruct.dto.UsuarioDto;
import com.svc.ventas.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

  private final IUsuarioService usuarioService;

  /**
   * Retorna el usuario logueado actualmente desde el contexto de Spring Security.
   */
  public UsuarioDto obtenerUsuarioLogueado() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalStateException("No hay usuario autenticado actualmente");
    }
    return usuarioService.getPorUserName(auth.getName());
  }
}
