package com.svc.ventas.service;

import com.svc.ventas.models.mapstruct.dto.ChartDTO;

import java.time.LocalDate;

public interface IDashboardService {

   ChartDTO getDashboard(LocalDate fecInicio , LocalDate fecFin);

}
