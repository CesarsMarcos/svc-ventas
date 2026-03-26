package com.svc.ventas.util;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.service.impl.IUsuarioContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

  private final IUsuarioContext usuarioService;

  /**
   * Retorna el usuario logueado actualmente desde el contexto de Spring Security.
   */
  public Usuario obtenerUsuarioLogueado() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalStateException("No hay usuario autenticado actualmente");
    }
    return usuarioService.getPorUserName(auth.getName());
  }
}
