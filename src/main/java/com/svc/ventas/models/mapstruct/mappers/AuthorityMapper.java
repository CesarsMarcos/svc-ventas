package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Rol;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.enums.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorityMapper {

  public Set<String> getRoles(Usuario usuario) {

    return usuario.getRoles()
            .stream()
            .map(Rol::getDesRol)
            .collect(Collectors.toSet());
  }

  public Set<String> getPermissions(Usuario usuario) {

    return usuario.getRoles()
            .stream()
            .map(rol -> Role.valueOf(rol.getDesRol()))
            .flatMap(role -> role.getPermissions().stream())
            .map(Enum::name)
            .collect(Collectors.toSet());
  }

  public Boolean setUsaEmpleados(Usuario usuario) {
    return usuario.getSucursal().getEmpresa().getIsUsaEmpleados();
  }

  public Boolean setAplicaImpuesto(Usuario usuario) {
    return usuario.getSucursal().getEmpresa().getIsUsaEmpleados();
  }

  public String setSucursal(Usuario usuario) {
    return usuario.getSucursal().getRazonSocial();
  }

}
