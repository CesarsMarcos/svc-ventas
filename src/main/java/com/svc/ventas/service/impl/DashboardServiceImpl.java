package com.svc.ventas.service.impl;

import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.mapstruct.dto.ChartDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoMasVendidoDTO;
import com.svc.ventas.models.mapstruct.dto.VariacionVentasDTO;
import com.svc.ventas.models.mapstruct.dto.VentasPorMesDTO;
import com.svc.ventas.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements IDashboardService {

  private final CompraRepository compraRepo;

  private final VentaRepo ventaRepo;

  private final ClienteRepo clienteRepo;

  private final ProveedorRepo proveedorRepo;

  private final ProductoVendidoRepository productoRepo;

  @Override
  public ChartDTO getDashboard(LocalDate fecInicio , LocalDate fecFin) {

    LocalDateTime fechaInicio = LocalDateTime.now().minusMonths(12);

    LocalDateTime inicio = null;
    LocalDateTime fin = null;

    if (fecInicio != null) {
      inicio = fecInicio.atStartOfDay();
    }

    if (fecFin != null) {
      fin = fecFin.atTime(23, 59, 59);
    }

    Long numCompras = compraRepo.countCompras(inicio, fin);

    Long numVentas = ventaRepo.countVentas(inicio, fin);

    Long numClientes = clienteRepo.numClientesActivos();

    Long numProveedores = proveedorRepo.findAll()
            .stream().filter(proveedor -> proveedor.getIndEstado().equals(true))
            .count();

    List<ProductoMasVendidoDTO> productosVendidos = productoRepo.obtenerTop10ProductosMasVendidos(inicio, fin);

    VariacionVentasDTO ventasHoy = obtenerVentasHoy();

    VariacionVentasDTO ventasSemana = obtenerVentasSemana();

    VariacionVentasDTO ventasMes = obtenerVentasMes();

    List<VentasPorMesDTO> ventasUltimos12Meses = ventaRepo.obtenerVentasUltimos12Meses(fechaInicio);

    return ChartDTO.builder()
            .numCompras(numCompras)
            .numVentas(numVentas)
            .numClientes(numClientes)
            .numProveedores(numProveedores)
            .ventasHoy(ventasHoy)
            .ventasSemana(ventasSemana)
            .ventasMes(ventasMes)
            .productos(productosVendidos)
            .ventas12Meses(ventasUltimos12Meses)
            .build();
  }

  public VariacionVentasDTO obtenerVentasHoy() {
    LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
    LocalDateTime finHoy = inicioHoy.plusDays(1).minusSeconds(1);

    LocalDateTime inicioAyer = inicioHoy.minusDays(1);
    LocalDateTime finAyer = inicioHoy.minusSeconds(1);

    BigDecimal ventasHoy = ventaRepo.obtenerSumaVentasPorRango(inicioHoy, finHoy);
    BigDecimal ventasAyer = ventaRepo.obtenerSumaVentasPorRango(inicioAyer, finAyer);

    return new VariacionVentasDTO(ventasHoy, ventasAyer);
  }

  public VariacionVentasDTO obtenerVentasSemana() {
    LocalDateTime inicioSemana = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
    LocalDateTime finSemana = LocalDateTime.now();

    LocalDateTime inicioSemanaPasada = inicioSemana.minusWeeks(1);
    LocalDateTime finSemanaPasada = finSemana.minusWeeks(1);

    BigDecimal ventasSemana = ventaRepo.obtenerSumaVentasPorRango(inicioSemana, finSemana);
    BigDecimal ventasSemanaPasada = ventaRepo.obtenerSumaVentasPorRango(inicioSemanaPasada, finSemanaPasada);

    return new VariacionVentasDTO(ventasSemana, ventasSemanaPasada);
  }

  public VariacionVentasDTO obtenerVentasMes() {
    LocalDateTime inicioMes = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    LocalDateTime finMes = LocalDateTime.now();

    LocalDateTime inicioMesPasado = inicioMes.minusMonths(1);
    LocalDateTime finMesPasado = finMes.minusMonths(1);

    BigDecimal ventasMes = ventaRepo.obtenerSumaVentasPorRango(inicioMes, finMes);
    BigDecimal ventasMesPasado = ventaRepo.obtenerSumaVentasPorRango(inicioMesPasado, finMesPasado);

    return new VariacionVentasDTO(ventasMes, ventasMesPasado);
  }
}
