package com.svc.ventas.service.dashboardStrategy;

import com.svc.ventas.message.request.DashboardFiltroRequest;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;

public interface DashboardStrategy {

  boolean aplica(DashboardFiltroRequest filtro);

  ChartDTO obtenerDatos(DashboardFiltroRequest filtro);

}
