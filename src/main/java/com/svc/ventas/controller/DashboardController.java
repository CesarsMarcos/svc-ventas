package com.svc.ventas.controller;

import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.service.dashboardStrategy.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/charts/")
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping
  public ResponseEntity<?> getCharts(DashboardFiltroRequest tipoFiltro) {
    return ResponseEntity.ok(dashboardService.obtenerDashboard(tipoFiltro));
  }

}
