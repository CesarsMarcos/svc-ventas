package com.svc.ventas.config;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppContext {

  private final SecurityUtils securityUtils;

  public Usuario getUsuario() {
    return securityUtils.obtenerUsuarioLogueado();
  }

  public Integer getusuarioId() {
    return getUsuario().getIdUsuario();
  }

  public Sucursal getSucursal() {
    Usuario usuario = getUsuario();

    if (Objects.nonNull(usuario.getSucursal())) {
      return usuario.getSucursal();
    }

    if (Objects.nonNull(usuario.getEmpleado().getSucursal())) {
      return usuario.getEmpleado().getSucursal();
    }

    throw new RuntimeException("Usuario suin sucursal");

  }

  public Long getSucursalId() {
    return getSucursal().getIdSucursal();
  }

  public Empresa getEmpresa() {
    return getSucursal().getEmpresa();
  }

  public Long getEmpresaId() {
    return getSucursal().getEmpresa().getIdEmpresa();
  }

  public String getUserName() {
    return getUsuario().getUsuario();
  }

}
