package com.svc.ventas.models;

import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

  private Usuario usuario;

  public CustomUserDetails(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Set<GrantedAuthority> authorities = new HashSet<>();

    usuario.getRoles().forEach(rol -> {

      Role role = Role.valueOf(rol.getDesRol());

      authorities.add(new SimpleGrantedAuthority(role.name()));

      role.getPermissions().forEach(permission ->
              authorities.add(new SimpleGrantedAuthority(permission.name()))
      );
    });

    return authorities;
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
