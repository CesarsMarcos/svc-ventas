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

  private final ClienteRepo clienteRepo;

  private final SucursalRepo sucursalRepo;

  private final ProductoStockRepo productoStockRepo;

  private final ICajaService cajaService;

  private final ProductoVendidoRepository productoVendidoRepo;

  private final VentaRepo ventaRepo;

  private final ProductoRepo productoRepo;

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
    Long idEmpresa  = appContext.getEmpresaId();

    log.info("Valida montos ::");
    log.info("Valida disponibilidad de stock para producto ::");
    VentaMontosDto ventaMontosDto = validarStockYCalcularMontos(venta, idSucursal);

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

    validarFacturaParaClienteFinal(clienteBD, tipoDocumento);

    log.info("Busca sucursal existente ::");
    Sucursal sucursalBD = sucursalRepo.findById(idSucursal)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Sucursal", idSucursal)));

    log.info("Validar correlativo ::");
    Serie serieBD = serieRepo.obtenerSerieForUpdate(idEmpresa, idSucursal, venta.getIdTipoDocumento() )
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Serie", venta.getIdTipoDocumento())));

    Long nextCorrelativo = serieBD.getCorrelativo() + 1 ;
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
              log.info("Busca producto y actualiza el stock del producto ::");
              Producto productoBD = productoRepo.findById(ppv.getIdProducto())
                      .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", ppv.getIdProducto())));

              log.info("Busca stock de producto en sucursal ::");
              ProductoStock productoStock = productoStockRepo.buscar(ppv.getIdProducto(), idSucursal)
                      .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

              productoStock.restarStock(ppv.getCantidad());

              log.info("Actualiza stock de producto :: {}, en local {} ", productoBD.getNombre(),
                      productoStock.getSucursal().getCodigo());
              productoStockRepo.save(productoStock);

              productoVendidoRepo.save(ProductoVendido
                      .builder()
                      .venta(ventaEntity)
                      .idProducto(productoBD.getIdProducto())
                      .descripcion(productoBD.getDescripcion())
                      .nombre(productoBD.getNombre())
                      .precio(productoStock.getPrecioVenta())
                      .subTotal(productoStock.getPrecioVenta().multiply(BigDecimal.valueOf(ppv.getCantidad())))
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
            //.serieCorrelativo()
            .build();
  }

  @Override
  public Map<String, Object> searchVenta(String nombre, String documentoCliente,
                                       String documentoVenta, LocalDate inicio,
                                       LocalDate fin,  Integer page, Integer size) {

    Sucursal currentSucursal = appContext.getSucursal();

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecAdd"));

    Specification<Venta> spec =  Specification
            .where(VentaSpecifications.hasClienteNombre(nombre))
            .and(VentaSpecifications.hasClienteDNI(documentoCliente))
            .and(VentaSpecifications.hasSucursal(currentSucursal))
            .and(VentaSpecifications.hasFechaBetween(inicio, fin));

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
            .map(tp ->  EnumDto.builder()
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
  public byte[] generarPdf (DetalleImpresionDto venta, com.svc.ventas.models.enums.TipoDocumento tipoDocumento) {
    DocumentoPdfStrategy strategy = documentoPdfFactory.obtener(tipoDocumento);
    return strategy.generar(venta);
  }

  private VentaMontosDto validarStockYCalcularMontos(VentaRequest venta, Long idSucursal) {

    BigDecimal subtotalCalculado = BigDecimal.ZERO;

    for (ProductoParaVender p : venta.getProductos()) {

      ProductoStock productoStock = productoStockRepo.buscar(p.getIdProducto(), idSucursal)
              .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

      if(BigDecimal.ZERO.compareTo(productoStock.getPrecioVenta()) == 0) {
        throw new BusinessException("Precio no registrado para el producto: " + productoStock.getProducto().getNombre());
      }

      if(productoStock.sinStock()) {
        throw new BusinessException("Stock insuficiente para el producto: " + productoStock.getProducto().getNombre() +
                ". Disponible: " + productoStock.getStock() + ", Solicitado: " + p.getCantidad());
      }

      if (productoStock.getStock() < p.getCantidad()) {
        throw new BusinessException("Stock insuficiente para el producto: " + productoStock.getProducto().getNombre() +
                ". Disponible: " + productoStock.getStock() + ", Solicitado: " + p.getCantidad());
      }

      BigDecimal subtotalProducto = productoStock.getPrecioVenta()
              .multiply(BigDecimal.valueOf(p.getCantidad()));

      subtotalCalculado = subtotalCalculado.add(subtotalProducto);
    }

    BigDecimal igvCalculado;
    BigDecimal totalCalculado;

    //if (Boolean.TRUE.equals(compra.getAplicarImpuesto())) {
    igvCalculado = subtotalCalculado.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
    totalCalculado = subtotalCalculado.add(igvCalculado);
    //}

    if (Objects.isNull(venta.getSubTotal()) ||
            venta.getSubTotal().setScale(2, RoundingMode.HALF_UP).compareTo(subtotalCalculado) != 0) {
      log.info("subtotal servidor: {}", subtotalCalculado);
      throw new ValidationException("El subtotal no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(venta.getIgv()) ||
            venta.getIgv().setScale(2, RoundingMode.HALF_UP).compareTo(igvCalculado) != 0) {
      log.info("IGV servidor: {}", igvCalculado);
      throw new ValidationException("El IGV no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(venta.getTotal()) ||
            venta.getTotal().setScale(2, RoundingMode.HALF_UP).compareTo(totalCalculado) != 0) {
      log.info("total servidor: {}", totalCalculado);
      throw new ValidationException("El total no coincide con el cálculo del servidor.");
    }

    log.info("Montos validados correctamente: Subtotal={}, IGV={}, Total={}",
            subtotalCalculado, igvCalculado, totalCalculado);

    return new VentaMontosDto(subtotalCalculado, igvCalculado, totalCalculado);
  }

  private static void validarFacturaParaClienteFinal(Cliente clienteBD, TipoDocumento tipoDocumento) {
    if(clienteBD.getPersona().getIsClienteGenerico() && "01".equalsIgnoreCase(tipoDocumento.getCodigoSunat())) {
      throw new BusinessException("No se puede generar una Factura para un cliente final");
    }
  }

}
