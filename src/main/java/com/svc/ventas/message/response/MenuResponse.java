package com.svc.ventas.message.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse {

  private String name;

  private String icon;

  private String routeLink;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<MenuResponse> submenus;

}
