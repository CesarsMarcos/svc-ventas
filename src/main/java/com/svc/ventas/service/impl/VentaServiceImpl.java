package com.svc.ventas.service.impl;

import static com.svc.ventas.util.Constantes.IGV;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.exception.ValidationException;
import com.svc.ventas.message.request.ProductoParaVender;
import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.message.response.ResponseTransaccion;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.enums.*;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.models.specifications.VentaSpecifications;
import com.svc.ventas.service.documentoStrategy.documento.DocumentoPdfFactory;
import com.svc.ventas.service.documentoStrategy.documento.DocumentoPdfStrategy;
import com.svc.ventas.util.AppUtils;
import jakarta.transaction.Transactional;

import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {

  private static final String FACTURA = "FACTURA";

  private static final String RUC = "RUC";

  private final ClienteRepo clienteRepo;

  private final SucursalRepo sucursalRepo;

  private final ProductoStockRepo productoStockRepo;

  private final ICajaService cajaService;

  private final ProductoVendidoRepository productoVendidoRepo;

  private final VentaRepo ventaRepo;

  private final SerieRepository serieRepo;

  private final TipoDocumentoRepository tipoDocumentoRepo;

  private final DocumentoPdfFactory documentoPdfFactory;

  private final VentaMapper ventaMapper;

  private final AppContext appContext;

  @Override
  @Transactional
  public ResponseTransaccion registrar(VentaRequest venta) {

    log.info("Iniciando registro de venta...");

    log.info("Obtiene usuario logueado ::");
    String usuario = appContext.getUserName();
    Long idSucursal = appContext.getSucursalId();
    Long idEmpresa = appContext.getEmpresaId();
    Boolean aplicaImpuesto = appContext.getEmpresa().getAplicaImpuesto();

    log.info("Valida montos ::");
    log.info("Valida disponibilidad de stock para producto ::");
    VentaMontosDto ventaMontosDto = validarStockYCalcularMontos(venta, idSucursal, aplicaImpuesto);

    log.info("Obtener caja activa ::");
    CajaDetalleDTO cajaDet = cajaService.findByFechaAndUsuario();
    if (!cajaDet.getExisteCajaActiva()) {
      throw new BusinessException(Constantes.MSJ_CAJA_NO_ABIERTA);
    }

    log.info("Busca cliente :: ");
    Cliente clienteBD = clienteRepo.findById(venta.getIdCliente())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Cliente", venta.getIdCliente())));

    log.info("Obtiene tipo de documento");
    TipoDocumento tipoDocumento = tipoDocumentoRepo.findById(venta.getIdTipoDocumento())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Tipo Documento", venta.getIdTipoDocumento())));

    if(FACTURA.equals(tipoDocumento.getDescripcion()) && (!RUC.equals(clienteBD.getPersona().getTipoDocumento().getValue()))) {
        throw new BusinessException("Debes seleccionar un cliente que tenga RUC para registrar una venta con Factura");
    }

    validarFacturaParaClienteFinal(clienteBD, tipoDocumento);

    log.info("Busca sucursal existente ::");
    Sucursal sucursalBD = sucursalRepo.findById(idSucursal)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", idSucursal)));

    log.info("Validar correlativo ::");
    Serie serieBD = serieRepo.obtenerSerieForUpdate(idEmpresa, idSucursal, venta.getIdTipoDocumento())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Serie", venta.getIdTipoDocumento())));

    Long nextCorrelativo = serieBD.getCorrelativo() + 1;
    log.info("Número de documento generado: {}", nextCorrelativo);

    log.info("Actualizar correlativo en series ::");
    serieBD.setCorrelativo(nextCorrelativo);

    serieRepo.save(serieBD);

    log.info("Registra nueva venta ::");
    Venta ventaNew = Venta.builder()
            .cliente(clienteBD)
            .sucursal(sucursalBD)
            .tipoDocumento(tipoDocumento)
            .tipoPago(TipoPago.valueOf(venta.getTipoPago()))
            .serie(serieBD.getSerie())
            .correlativo(serieBD.getCorrelativo())
            .igv(ventaMontosDto.getIgv())
            .subTotal(ventaMontosDto.getSubTotal())
            .total(ventaMontosDto.getTotal())
            .estado(EstadoVenta.CREADO)
            .createdBy(usuario)
            .build();

    Venta ventaEntity = ventaRepo.save(ventaNew);
    log.info("Venta guardada con ID: {}", ventaEntity.getIdVenta());

    log.info("Registra los productos a vender :: ");

    venta.getProductos()
            .forEach(ppv -> {

              log.info("Busca stock de producto en sucursal ::");
              ProductoStock productoStockBD = productoStockRepo.buscar(ppv.getIdProducto(), idSucursal)
                      .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

              ProductoStockPresentacion equivalencia = productoStockBD.getPresentaciones()
                      .stream().filter(presentacion -> presentacion.getIdPresentacion()
                              .equals(ppv.getIdPresentacion()))
                      .findFirst().orElse(null);

              productoStockBD.restarStock(ppv.getCantidad().multiply(equivalencia.getEquivalencia()));

              log.info("Actualiza stock de producto :: {}, en local {} ", productoStockBD.getProducto().getNombre(),
                      productoStockBD.getSucursal().getCodigo());
              productoStockRepo.save(productoStockBD);

              productoVendidoRepo.save(ProductoVendido
                      .builder()
                      .venta(ventaEntity)
                      .idProducto(productoStockBD.getProducto().getIdProducto())
                      .descripcion(productoStockBD.getProducto().getDescripcion())
                      .nombre(productoStockBD.getProducto().getNombre())
                      .precio(equivalencia.getPrecioVenta())
                      .subTotal(equivalencia.getPrecioVenta().multiply(ppv.getCantidad()))
                      .precioDescuento(BigDecimal.ZERO)
                      .cantidad(ppv.getCantidad())
                      .build());
            });

    CajaMovimientosDTO mov = CajaMovimientosDTO
            .builder()
            .tipoMovimiento(TipoMovimiento.INGRESO)
            .documento(serieBD.getSerie().concat("-").concat(AppUtils.formatearSunat(nextCorrelativo)))
            .monto(ventaNew.getTotal())
            .tipoPago(TipoPago.EFECTIVO)
            .origen(OrigenMovimiento.VENTA)
            .build();
    cajaService.agregarMovimiento(cajaDet.getIdCaja(), mov);
    log.info("Venta registrada correctamente con número {}", serieBD.getCorrelativo());

    return ResponseTransaccion
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .tipoDocumento(ventaEntity.getTipoDocumento().getDescripcion())
            .total(ventaEntity.getTotal())
            .tipoPago(ventaEntity.getTipoPago().getLabel())
            .serieCorrelativo(ventaEntity.getSerie().concat("-").concat(AppUtils.formatearSunat(ventaEntity.getCorrelativo())))
            .build();
  }

  @Override
  public Map<String, Object> searchVenta(String nombre, String documentoCliente,
                                         String documentoVenta, LocalDate inicio,
                                         LocalDate fin, Integer page, Integer size) {

    Sucursal currentSucursal = appContext.getSucursal();

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecAdd"));

    Specification<Venta> spec = Specification
            .where(VentaSpecifications.hasClienteNombre(nombre))
            .and(VentaSpecifications.hasClienteDNI(documentoCliente))
            .and(VentaSpecifications.hasSucursal(currentSucursal))
            .and(VentaSpecifications.hasFechaBetween(inicio, fin))
            .and(VentaSpecifications.filtroSeguridad(appContext));

    Page<Venta> pageVenta = ventaRepo.findAll(spec, pageable);

    List<VentaGetDto> ventaDto = pageVenta.getContent()
            .stream()
            .map(ventaMapper::mapToVentaGetDto)
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("ventas", ventaDto);
    response.put("currentPage", pageVenta.getNumber());
    response.put("totalItems", pageVenta.getTotalElements());
    response.put("totalPages", pageVenta.getTotalPages());

    return response;

  }

  @Override
  public VentaDetailDto details(Long id) {
    return ventaRepo.findById(id)
            .map(ventaMapper::mapToVentaDetailDto)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Venta", id)));
  }

  @Override
  public List<Venta> listadoVentasPorCliente(String dni/*, String fecha*/) {
    return ventaRepo.ventasPorDocumentoCliente(dni);
  }

  @Override
  public List<EnumDto> tipoPago() {
    return Arrays.stream(TipoPago.values())
            .map(tp -> EnumDto.builder()
                    .value(tp.getValue())
                    .label(tp.getLabel())
                    .build())
            .toList();
  }

  @Override
  public List<EnumDto> tipoDocumentoPersona() {
    return Arrays.stream(TipoDocumentoPersona.values())
            .map(td -> EnumDto.builder()
                    .label(td.getLabel())
                    .value(td.getValue())
                    .build())
            .toList();
  }

  @Override
  public byte[] generarPdf(DetalleImpresionDto venta, com.svc.ventas.models.enums.TipoDocumento tipoDocumento) {
    DocumentoPdfStrategy strategy = documentoPdfFactory.obtener(tipoDocumento);
    return strategy.generar(venta);
  }

  @Override
  public List<ProductoSearchVentaDto> buscarPorNombreOCodigoPresentacionesParaVenta(String termino) {
    if (Objects.isNull(termino) || termino.trim().isEmpty()) {
      return Collections.emptyList();
    }
    Long idSucursal = appContext.getSucursalId();
    return ventaRepo.buscarPorNombreOCodigoPresentacionesParaVenta(termino, idSucursal);
  }

  @Override
  public Map<String, Object> searchProductosVentaPos(String nombre,
                                                     Long categoriaId, int page, int size) {

    Long idSucursal = appContext.getSucursalId();
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductoSearchVentaDto> pageVenta =
            ventaRepo.buscarPorNombreOCodigoPresentacionesParaVentaPos(nombre, categoriaId, idSucursal, pageable);

    Map<String, Object> response = new HashMap<>();
    response.put("productos", pageVenta.getContent());
    response.put("currentPage", pageVenta.getNumber());
    response.put("totalItems", pageVenta.getTotalElements());
    response.put("totalPages", pageVenta.getTotalPages());

    return response;

  }

  private VentaMontosDto validarStockYCalcularMontos(VentaRequest venta, Long idSucursal, Boolean aplicaImpuesto) {

    BigDecimal subtotalCalculado = BigDecimal.ZERO;

    for (ProductoParaVender p : venta.getProductos()) {

      ProductoStock productoStock = productoStockRepo.buscar(p.getIdProducto(), idSucursal)
              .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

      if (productoStock.sinStock() || productoStock.getStock().compareTo(p.getCantidad()) < 0) {
        throw new BusinessException(
                "Stock insuficiente para el producto: " + productoStock.getProducto().getNombre() +
                        ". Disponible: " + productoStock.getStock() +
                        ", Solicitado: " + p.getCantidad()
        );
      }

      BigDecimal precioVenta = productoStock.getPresentaciones()
              .stream()
              .filter(pre -> Objects.equals(pre.getIdPresentacion(), p.getIdPresentacion()))
              .findFirst()
              .map(ProductoStockPresentacion::getPrecioVenta)
              .orElseThrow(() -> new ValidationException(
                      "No se encontró presentación válida para el producto: "
                              + productoStock.getProducto().getNombre()
              ));

      BigDecimal subtotalProducto = precioVenta.multiply(p.getCantidad());
      subtotalCalculado = subtotalCalculado.add(subtotalProducto);
    }

    subtotalCalculado = subtotalCalculado.setScale(2, RoundingMode.HALF_UP);

    BigDecimal igvCalculado = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    BigDecimal totalCalculado = subtotalCalculado;

    if (Boolean.TRUE.equals(aplicaImpuesto)) {
      igvCalculado = subtotalCalculado.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
      totalCalculado = subtotalCalculado.add(igvCalculado).setScale(2, RoundingMode.HALF_UP);
    }


    if (Objects.isNull(venta.getSubTotal()) ||
            venta.getSubTotal().setScale(2, RoundingMode.HALF_UP)
                    .compareTo(subtotalCalculado) != 0) {

      log.info("Subtotal recibido: {}", venta.getSubTotal());
      log.info("Subtotal servidor: {}", subtotalCalculado);

      throw new ValidationException("El subtotal no coincide con el cálculo del servidor.");
    }

    if (Boolean.TRUE.equals(aplicaImpuesto)) {
      if (Objects.isNull(venta.getIgv()) ||
              venta.getIgv().setScale(2, RoundingMode.HALF_UP)
                      .compareTo(igvCalculado) != 0) {

        log.info("IGV recibido: {}", venta.getIgv());
        log.info("IGV servidor: {}", igvCalculado);

        throw new ValidationException("El IGV no coincide con el cálculo del servidor.");
      }
    } else {
      BigDecimal igvRecibido = Objects.isNull(venta.getIgv()) ? BigDecimal.ZERO
              : venta.getIgv().setScale(2, RoundingMode.HALF_UP);

      if (igvRecibido.compareTo(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)) != 0) {
        throw new ValidationException("La venta no debe incluir IGV.");
      }
    }

    if (Objects.isNull(venta.getTotal()) ||
            venta.getTotal().setScale(2, RoundingMode.HALF_UP)
                    .compareTo(totalCalculado) != 0) {

      log.info("Total recibido: {}", venta.getTotal());
      log.info("Total servidor: {}", totalCalculado);

      throw new ValidationException("El total no coincide con el cálculo del servidor.");
    }

    log.info("Montos validados correctamente: Subtotal={}, IGV={}, Total={}",
            subtotalCalculado, igvCalculado, totalCalculado);

    return new VentaMontosDto(subtotalCalculado, igvCalculado, totalCalculado);
  }

  private static void validarFacturaParaClienteFinal(Cliente clienteBD, TipoDocumento tipoDocumento) {
    if (clienteBD.getIsClienteGenerico() && "01".equalsIgnoreCase(tipoDocumento.getCodigoSunat())) {
      throw new BusinessException("No se puede generar una Factura para un cliente final");
    }
  }

}
