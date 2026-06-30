package com.svc.ventas.service.impl;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements IDashboardService {

  private final VentaRepo ventaRepo;

  private final ClienteRepo clienteRepo;

  private final ProveedorRepo proveedorRepo;

  private final ProductoVendidoRepository productoRepo;

  private final AppContext appContext;

  @Override
  public ChartDTO getDashboard(LocalDate fecInicio, LocalDate fecFin) {

    LocalDateTime inicio = null;
    LocalDateTime fin = null;

    Long idSucursal = appContext.getSucursalId();

    if (fecInicio != null) {
      inicio = fecInicio.atStartOfDay();
    }

    if (fecFin != null) {
      fin = fecFin.atTime(23, 59, 59);
    }

    BigDecimal valorVentas = ventaRepo.obtenerSumaVentasPorRango(inicio, fin, idSucursal)
            .setScale(2, RoundingMode.HALF_UP);

    Long numVentas = ventaRepo.countVentas(inicio, fin, idSucursal);

    Long numClientes = clienteRepo.numClientesActivos();

    Long numProveedores = proveedorRepo.numProveedoresActivos();

    List<ProductoMasVendidoDTO> productosMasVendidos = productosVendidos(inicio, fin, idSucursal);

    List<BajoStockDTO> productosBajoStock = obtenerProductosBajoStock(idSucursal);

    List<UltimasVentasDTO> ultimasVentas = obtenerUltimasVentas(idSucursal);

    return ChartDTO.builder()
            .valorVentas(valorVentas)
            .numVentas(numVentas)
            .numClientes(numClientes)
            .numProveedores(numProveedores)
            .productosMasVendidos(productosMasVendidos)
            .productosBajoStock(productosBajoStock)
            .ultimasVentas(ultimasVentas)
            .build();
  }

  private VariacionVentasDTO obtenerVentasHoy() {

    Long idSucursal = appContext.getSucursalId();

    LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
    LocalDateTime finHoy = inicioHoy.plusDays(1).minusSeconds(1);

    LocalDateTime inicioAyer = inicioHoy.minusDays(1);
    LocalDateTime finAyer = inicioHoy.minusSeconds(1);

    BigDecimal ventasHoy = ventaRepo.obtenerSumaVentasPorRango(inicioHoy, finHoy, idSucursal);
    BigDecimal ventasAyer = ventaRepo.obtenerSumaVentasPorRango(inicioAyer, finAyer, idSucursal);

    return new VariacionVentasDTO(ventasHoy, ventasAyer);
  }

  private VariacionVentasDTO obtenerVentasSemana() {

    Long idSucursal = appContext.getSucursalId();

    LocalDateTime inicioSemana = LocalDate.now()
            .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
    LocalDateTime finSemana = LocalDateTime.now();

    LocalDateTime inicioSemanaPasada = inicioSemana.minusWeeks(1);
    LocalDateTime finSemanaPasada = finSemana.minusWeeks(1);

    BigDecimal ventasSemana = ventaRepo.obtenerSumaVentasPorRango(inicioSemana, finSemana, idSucursal);
    BigDecimal ventasSemanaPasada = ventaRepo.obtenerSumaVentasPorRango(inicioSemanaPasada, finSemanaPasada, idSucursal);

    return new VariacionVentasDTO(ventasSemana, ventasSemanaPasada);
  }

  private VariacionVentasDTO obtenerVentasMes() {

    Long idSucursal = appContext.getSucursalId();

    LocalDateTime inicioMes = LocalDate.now()
            .with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    LocalDateTime finMes = LocalDateTime.now();

    LocalDateTime inicioMesPasado = inicioMes.minusMonths(1);
    LocalDateTime finMesPasado = finMes.minusMonths(1);

    BigDecimal ventasMes = ventaRepo.obtenerSumaVentasPorRango(inicioMes, finMes, idSucursal);
    BigDecimal ventasMesPasado = ventaRepo.obtenerSumaVentasPorRango(inicioMesPasado, finMesPasado, idSucursal);

    return new VariacionVentasDTO(ventasMes, ventasMesPasado);
  }

  private List<ProductoMasVendidoDTO> productosVendidos(LocalDateTime inicio, LocalDateTime fin, Long idSucursal) {
    return productoRepo.obtenerTop10ProductosMasVendidos(inicio, fin, idSucursal);
  }

  private List<BajoStockDTO> obtenerProductosBajoStock(Long idSucursal) {
    return ventaRepo.obtenerProductosBajoStock(idSucursal);
  }

  private List<UltimasVentasDTO> obtenerUltimasVentas(Long idSucursal) {
    return ventaRepo.obtenerUltimas5Ventas(idSucursal);
  }

}
