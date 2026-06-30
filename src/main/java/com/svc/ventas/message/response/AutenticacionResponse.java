package com.svc.ventas.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class AutenticacionResponse {
  private String sucursal;
  private String accessToken;
  private String refreshToken;
  private Boolean isUsaEmpleado;
  private Boolean estadoCaja;
  private Boolean aplicaImpuesto;
  private Set<String> permissions;
  private List<MenuResponse> menus;
}
