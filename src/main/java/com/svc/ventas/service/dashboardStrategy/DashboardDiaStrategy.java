package com.svc.ventas.service.dashboardStrategy;

import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.models.enums.TipoFiltroDashboard;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;
import com.svc.ventas.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardDiaStrategy implements DashboardStrategy {

  private final IDashboardService dashboardService;

  @Override
  public boolean aplica(DashboardFiltroRequest filtro) {
    return filtro.getTipoFiltro() == TipoFiltroDashboard.DIA;
  }

  @Override
  public ChartDTO obtenerDatos(DashboardFiltroRequest filtro) {
    LocalDate inicio = LocalDate.now();
    LocalDate fin = LocalDate.now();
    return dashboardService.getDashboard(inicio, fin);
  }
}
