package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoFiltroDashboard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DashboardFiltroRequest {

  private TipoFiltroDashboard tipoFiltro;

  private LocalDate fechaInicio;

  private LocalDate fechaFin;

}
