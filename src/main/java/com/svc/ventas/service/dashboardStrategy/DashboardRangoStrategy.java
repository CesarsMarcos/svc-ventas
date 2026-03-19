
package com.svc.ventas.service.dashboardStrategy;

import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;
import com.svc.ventas.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardRangoStrategy implements DashboardStrategy {

  private final IDashboardService dashboardService;

  @Override
  public boolean aplica(DashboardFiltroRequest filtro) {
    return filtro.getFechaInicio() != null && filtro.getFechaFin() != null;
  }

  @Override
  public ChartDTO obtenerDatos(DashboardFiltroRequest filtro) {
    return dashboardService.getDashboard(filtro.getFechaInicio(), filtro.getFechaFin());
  }
}
