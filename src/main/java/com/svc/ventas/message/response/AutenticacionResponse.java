package com.svc.ventas.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AutenticacionResponse {
    private String accessToken;
    private String refreshToken;
    private List<MenuResponse> menus;
}
