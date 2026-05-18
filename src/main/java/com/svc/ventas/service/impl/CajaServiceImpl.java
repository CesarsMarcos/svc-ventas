package com.svc.ventas.service.impl;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.request.CajaAperturaRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.CajaRepo;
import com.svc.ventas.models.dao.MovimientoRepo;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.enums.EstadoCaja;
import com.svc.ventas.models.enums.OrigenMovimiento;
import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.models.mapstruct.mappers.CajaMapper;
import com.svc.ventas.models.mapstruct.mappers.MovimientoMapper;
import com.svc.ventas.models.mapstruct.mappers.UsuarioMapper;
import com.svc.ventas.service.ICajaService;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CajaServiceImpl implements ICajaService {

  private final CajaRepo cajaRepo;

  private final MovimientoRepo movimientoRepo;

  private final CajaMapper cajaMapper;

  private final UsuarioMapper usuarioMapper;

  private final AppContext appContext;

  @Override
  public CajaDetalleDTO findByFechaAndUsuario() {

    String currentUserName = appContext.getUserName();
    Long idEmpresa = appContext.getEmpresaId();

    LocalDate fecha = LocalDate.now();
    LocalDateTime inicio = fecha.atStartOfDay();
    LocalDateTime fin = fecha.atTime(23, 59, 59);

    return cajaRepo.findByFecha(inicio, fin, currentUserName, idEmpresa)
            .map(this::construirCajaAbiertaDTO)
            .orElseGet(this::construirCajaCerradaDTO);
  }

  @Override
  public Response aperturaCaja(CajaAperturaRequest caja) {

    Usuario currentUsuario = appContext.getUsuario();
    Sucursal currentSucursal = appContext.getSucursal();
    Empresa empresa = appContext.getEmpresa();

    LocalDate fecha = LocalDate.now();

    validarCajaExistenteParaUsuario(fecha, currentUsuario.getUsuario(), empresa.getIdEmpresa());

    cajaRepo.save(Caja.builder()
            .usuario(currentUsuario)
            .createdBy(currentUsuario.getUsuario())
            .montoApertura(caja.getMontoApertura())
            .fechaHoraApertura(LocalDateTime.now())
            .estado(EstadoCaja.ABIERTA)
            .empresa(empresa)
            .sucursal(currentSucursal)
            .build());

    return Response.builder().mensaje(Constantes.MENSAJE_SAVE).build();
  }

  @Override
  public Response cerrarCaja(Long idCaja) {
    cajaRepo.findById(idCaja)
            .map(cajaSave -> {
              cajaSave.setFechaHoraCierre(LocalDateTime.now());
              cajaSave.setEstado(EstadoCaja.CERRADA);
              return cajaRepo.save(cajaSave);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, CajaServiceImpl.class, idCaja)));

    return Response.builder().mensaje(Constantes.MENSAJE_CAJA_CERRADA).build();
  }

  @Override
  public Response agregarMovimiento(Long idCaja, CajaMovimientosDTO movimientoDTO) {
    Caja caja = cajaRepo.findById(idCaja)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, CajaServiceImpl.class, idCaja)));

    if("CERRADO".equalsIgnoreCase(String.valueOf(caja.getEstado()))){
      throw new ConflictException(Constantes.MSJ_CAJA_CERRADA);
    }

    if (movimientoDTO.getTipoMovimiento() == TipoMovimiento.INGRESO
            && Objects.isNull(movimientoDTO.getTipoPago())) {
      throw new IllegalArgumentException("El tipo de pago es obligatorio para ingresos");
    }

    CajaMovimiento nuevoMovimiento = MovimientoMapper.INSTANCE.toEntity(movimientoDTO);
    nuevoMovimiento.setCaja(caja);

    movimientoRepo.save(nuevoMovimiento);

    return Response.builder().mensaje(String.format(Constantes.MSJ_MOVIMIENTO_AGREGADO, idCaja)).build();
  }

  @Override
  public ResumenCajaDTO calcularCierreCaja(Long idCaja) {
    Caja caja = cajaRepo.findById(idCaja)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, CajaServiceImpl.class, idCaja)));

    Map<TipoPago, BigDecimal> totalesPorPago = inicializarTotalesPorPago();
    Map<TipoMovimiento, BigDecimal> totalesPorMovimiento = inicializarTotalesPorMovimiento();

    TotalesCaja totales = procesarMovimientos(caja, totalesPorPago, totalesPorMovimiento);

    TotalesFinales finales = calcularTotales(caja, totales, totalesPorPago, totalesPorMovimiento);

    return buildResumenCajaDTO(caja, totalesPorPago, totalesPorMovimiento, totales, finales);
  }

  private void validarCajaExistenteParaUsuario(LocalDate fecha, String nombreUsuario,
                                               Long idEmpresa) {

    LocalDateTime fin = fecha.atTime(23, 59, 59);

    boolean existeCaja = cajaRepo.findByFecha(fecha.atStartOfDay(),fin,
            nombreUsuario, idEmpresa).isPresent();

    if (existeCaja) {
      String mensaje = String.format(Constantes.MSJ_CAJA_EXISTE, nombreUsuario, fecha);
      throw new ConflictException(mensaje);
    }
  }

  private Map<TipoPago, BigDecimal> inicializarTotalesPorPago() {
    Map<TipoPago, BigDecimal> map = new EnumMap<>(TipoPago.class);
    for (TipoPago tipo : TipoPago.values()) {
      map.put(tipo, BigDecimal.ZERO);
    }
    return map;
  }

  private Map<TipoMovimiento, BigDecimal> inicializarTotalesPorMovimiento() {
    Map<TipoMovimiento, BigDecimal> map = new EnumMap<>(TipoMovimiento.class);
    for (TipoMovimiento tipo : TipoMovimiento.values()) {
      map.put(tipo, BigDecimal.ZERO);
    }
    return map;
  }

  private TotalesCaja procesarMovimientos(
          Caja caja,
          Map<TipoPago, BigDecimal> totalesPorPago,
          Map<TipoMovimiento, BigDecimal> totalesPorMovimiento) {

    BigDecimal totalVentas = BigDecimal.ZERO;
    BigDecimal totalIngresos = BigDecimal.ZERO;
    BigDecimal totalEgresos = BigDecimal.ZERO;

    if (Objects.nonNull(caja.getMovimientos())) {
      for (CajaMovimiento m : caja.getMovimientos()) {
        BigDecimal monto = Objects.requireNonNullElse(m.getMonto(), BigDecimal.ZERO);

        if (Objects.nonNull(m.getTipoMovimiento())) {
          totalesPorMovimiento.merge(m.getTipoMovimiento(), monto, BigDecimal::add);

          if (m.getTipoMovimiento() == TipoMovimiento.INGRESO) {
            totalIngresos = totalIngresos.add(monto);

            if (m.getOrigen() == OrigenMovimiento.VENTA) {
              totalVentas = totalVentas.add(monto);
            }
            if (Objects.nonNull(m.getTipoPago())) {
              totalesPorPago.merge(m.getTipoPago(), monto, BigDecimal::add);
            }

          } else {
            totalEgresos = totalEgresos.add(monto);
          }
        }
      }
    }
    return new TotalesCaja(totalVentas, totalIngresos, totalEgresos);
  }

  private TotalesFinales calcularTotales(
          Caja caja,
          TotalesCaja totales,
          Map<TipoPago, BigDecimal> totalesPorPago,
          Map<TipoMovimiento, BigDecimal> totalesPorMovimiento) {

    BigDecimal montoInicial = caja.getMontoApertura();

    BigDecimal totalEfectivo = totalesPorPago.getOrDefault(TipoPago.EFECTIVO, BigDecimal.ZERO);

    BigDecimal devoluciones = totalesPorMovimiento.getOrDefault(TipoMovimiento.DEVOLUCIONES, BigDecimal.ZERO);
    BigDecimal prestamos = totalesPorMovimiento.getOrDefault(TipoMovimiento.PRESTAMOS, BigDecimal.ZERO);
    BigDecimal gastos = totalesPorMovimiento.getOrDefault(TipoMovimiento.GASTOS, BigDecimal.ZERO);

    BigDecimal totalEfectivoEnCaja = totalEfectivo
            .add(montoInicial)
            .subtract(totales.getTotalEgresos())
            .subtract(prestamos)
            .subtract(gastos)
            .add(devoluciones);

    BigDecimal totalCtaBancaria = calcularTotalCtaBancaria(totalesPorPago);

    BigDecimal totalCuadre = totalEfectivoEnCaja.add(totalCtaBancaria);

    return new TotalesFinales(totalEfectivoEnCaja, totalCtaBancaria, totalCuadre);
  }

  private BigDecimal calcularTotalCtaBancaria(Map<TipoPago, BigDecimal> totalesPorPago) {
    return Stream.of(TipoPago.TARJETA, TipoPago.TRANSFERENCIA, TipoPago.YAPE, TipoPago.PLIN)
            .map(tipo -> totalesPorPago.getOrDefault(tipo, BigDecimal.ZERO))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private ResumenCajaDTO buildResumenCajaDTO(
          Caja caja,
          Map<TipoPago, BigDecimal> totalesPorPago,
          Map<TipoMovimiento, BigDecimal> totalesPorMovimiento,
          TotalesCaja totales,
          TotalesFinales finales) {

    BigDecimal saldo = totales.getTotalIngresos().subtract(totales.getTotalEgresos());
    BigDecimal montoInicialMasSaldo = caja.getMontoApertura().add(saldo);

    return cajaMapper.toResumenCajaDTO(caja, totalesPorPago,
            totalesPorMovimiento, saldo, montoInicialMasSaldo, totales, finales);
  }


  private CajaDetalleDTO construirCajaAbiertaDTO (Caja caja){

    Map<TipoPago, BigDecimal> totalesPorPago = inicializarTotalesPorPago();

    Map<TipoMovimiento, BigDecimal> totalesPorMovimiento = inicializarTotalesPorMovimiento();

    TotalesCaja totales = procesarMovimientos(caja, totalesPorPago, totalesPorMovimiento);

    return CajaDetalleDTO.builder()
            .IdCaja(caja.getIdCaja())
            .estado(caja.getEstado())
            .existeCajaActiva(true)
            .dataCaja(cajaMapper.toModelDto(caja, totales))
            .build();
  }

  private CajaDetalleDTO construirCajaCerradaDTO(){
    return CajaDetalleDTO.builder()
            .IdCaja(null)
            .estado(EstadoCaja.CERRADA)
            .existeCajaActiva(false)
            .dataCaja(null)
            .build();
  }
}
