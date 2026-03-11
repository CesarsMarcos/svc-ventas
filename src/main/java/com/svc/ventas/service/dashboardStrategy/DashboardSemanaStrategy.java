package com.svc.ventas.service.dashboardStrategy;

import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.models.enums.TipoFiltroDashboard;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;
import com.svc.ventas.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardSemanaStrategy implements DashboardStrategy {

  private final IDashboardService dashboardService;

  @Override
  public boolean aplica(DashboardFiltroRequest filtro) {
    return filtro.getTipoFiltro() == TipoFiltroDashboard.SEMANA;
  }

  @Override
  public ChartDTO obtenerDatos(DashboardFiltroRequest filtro) {
    LocalDate hoy = LocalDate.now();

    LocalDate inicio = hoy.with(DayOfWeek.MONDAY);
    LocalDate fin = hoy.with(DayOfWeek.SUNDAY);

    return dashboardService.getDashboard(inicio, fin);
  }
}
