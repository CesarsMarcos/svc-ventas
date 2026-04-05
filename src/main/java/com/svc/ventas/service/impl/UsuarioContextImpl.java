package com.svc.ventas.service.impl;

import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.models.dao.UsuarioRepo;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.service.IUsuarioContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioContextImpl implements IUsuarioContext {

  private final UsuarioRepo usuarioRepo;

  @Override
  public Usuario getPorUserName(String username) {
    return usuarioRepo.getByUserName(username)
            .orElseThrow(() ->
                    new EntityNotFoundException("no se encontro usuario registrado"));
  }
}
