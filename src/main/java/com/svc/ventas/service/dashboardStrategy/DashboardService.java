package com.svc.ventas.service.dashboardStrategy;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

  private final List<DashboardStrategy> strategies;

  public ChartDTO obtenerDashboard(DashboardFiltroRequest filtroRequest) {

    DashboardStrategy strategy = strategies.stream()
            .filter(s -> s.aplica(filtroRequest))
            .findFirst()
            .orElseThrow(() -> new BusinessException("No hay estrategia valida"));
    return strategy.obtenerDatos(filtroRequest);
  }

}
