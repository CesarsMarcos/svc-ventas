package com.svc.ventas.service;

import com.svc.ventas.models.CustomUserDetails;
import com.svc.ventas.models.dao.UsuarioRepo;
import com.svc.ventas.models.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UsuarioRepo usuarioRepo;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Usuario usuario =  usuarioRepo.getByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en Base de datos"));
    return new CustomUserDetails(usuario);
  }

}
