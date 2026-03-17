package com.svc.ventas.models;

import com.svc.ventas.models.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

  private Usuario usuario;

  public CustomUserDetails(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getDesRol().toUpperCase()))
            .toList();
  }

  @Override
  public String getPassword() {
    return usuario.getClave();
  }

  @Override
  public String getUsername() {
    return usuario.getUsuario();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
