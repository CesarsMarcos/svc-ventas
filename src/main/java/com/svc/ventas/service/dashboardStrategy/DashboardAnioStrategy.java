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
public class DashboardAnioStrategy implements DashboardStrategy {

  private final IDashboardService dashboardService;

  @Override
  public boolean aplica(DashboardFiltroRequest filtro) {
    return filtro.getTipoFiltro() == TipoFiltroDashboard.ANIO;
  }

  @Override
  public ChartDTO obtenerDatos(DashboardFiltroRequest filtro) {
    LocalDate inicio = LocalDate.now().minusYears(1);
    LocalDate fin = inicio.plusYears(1).minusDays(1);

    return dashboardService.getDashboard(inicio, fin);
  }
}
