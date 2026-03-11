package com.svc.ventas.service.impl;

import static com.svc.ventas.util.Constantes.IGV;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.ConflictException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.exception.ValidationException;
import com.svc.ventas.message.request.ProductoParaVender;
import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.enums.*;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.models.specifications.VentaSpecifications;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.SecurityUtils;
import jakarta.transaction.Transactional;

import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.util.Constantes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {

  private final IClienteService clienteService;

  private final ISucursalService sucursalService;

  private final IProductoService productoService;

  private final ProductoStockRepo productoStockRepo;

  private final ISerieService serieService;

  private final ICajaService cajaService;

  private final ProductoVendidoRepository productoVendidoRepo;

  private final VentaRepo ventaRepo;

  private final ProductoRepo productoRepo;

  private final SerieRepository serieRepo;

  private final VentaMapper ventaMapper;

  private final ClienteMapper clienteMapper;

  private final SucursalMapper sucursalMapper;

  private final ProductoMapper productoMapper;

  private final SecurityUtils securityUtils;

  @Override
  @Transactional
  public Response registrar(VentaRequest venta) {

    log.info("Iniciando registro de venta...");

    log.info("Valida montos ::");
    VentaMontosDto ventaMontosDto = validarStockYCalcularMontos(venta);

    log.info("Obtener caja activa ::");
    CajaDetalleDTO cajaDet = cajaService.findByFechaAndUsuario();
    if (!cajaDet.getExisteCajaActiva()) {
      throw new BusinessException(Constantes.MSJ_CAJA_NO_ABIERTA);
    }

    log.info("Busca cliente :: ");
    ClienteDto clienteDto = clienteService.obtener(venta.getIdCliente());

    log.info("Busca sucursal existente ::");
    SucursalDto sucursal = sucursalService.obtener(venta.getIdSucursal());

    log.info("Obtiene usuario logueado ::");
    Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();

    log.info("Validar correlativo ::");
    Serie serieBD = serieRepo.findForUpdateBySucursalIdSucursalAndTipoDocumento
            (usuarioLogueado.getEmpleado().getSucursal().getIdSucursal(), venta.getTipoDocumento())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Serie", venta.getTipoDocumento())));

    int nextCorrelativo = serieBD.getCorrelativo() + 1 ;
    log.info("Número de documento generado: {}", nextCorrelativo);

    log.info("Actualizar correlativo en series ::");
    serieBD.setCorrelativo(nextCorrelativo);
    serieService.save(serieBD);

    Venta ventaNew = Venta.builder()
            .cliente(clienteMapper.mapDtoToEntity(clienteDto))
            .sucursal(sucursalMapper.mapToSucursalPost(sucursal))
            .tipoDocumento(venta.getTipoDocumento())
            .tipoPago(TipoPago.valueOf(venta.getTipoPago()))
            .serie(serieBD.getSerie())
            .correlativo(serieBD.getCorrelativo())
            .igv(ventaMontosDto.getIgv())
            .subTotal(ventaMontosDto.getSubTotal())
            .total(ventaMontosDto.getTotal())
            .fecha(AppUtils.convert(venta.getFecha()))
            .estado(EstadoVenta.CREADO)
            .createdBy(usuarioLogueado.getUsuario())
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
              ProductoStock productoStock = productoStockRepo.buscar(ppv.getIdProducto(), venta.getIdSucursal())
                      .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

              log.info("Valida disponibilidad de stock para producto :: {} ", productoBD.getNombre());
              validarStock(ppv, productoStock);

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
                      .precio(productoStock.getPrecioVenta().multiply(BigDecimal.valueOf(ppv.getCantidad())))
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

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public List<VentaGetDto> searchVenta(Boolean isViewMore) {
    return List.of();
  }

  @Override
  public Map<String, Object> searchVenta(String nombre, String documentoCliente,
                                       String documentoVenta, LocalDate inicio,
                                       LocalDate fin,  Pageable pageable) {

    Specification<Venta> spec =  Specification
            .where(VentaSpecifications.hasClienteNombre(nombre))
            .and(VentaSpecifications.hasClienteDNI(documentoCliente))
            //.and(VentaSpecifications.hasDocumento(documentoVenta))
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
  public Object details(Long id) {
    return ventaRepo.findById(id)
            .map(ventaMapper::mapToVentaDetailDto)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Venta", id)));
  }

  @Override
  public List<Venta> listadoVentasPorCliente(String dni/*, String fecha*/) {
    return ventaRepo.ventasPorDocumentoCliente(dni);
  }

  private static void validarStock(ProductoParaVender ppv,  ProductoStock productoStock ) {

  }

  private VentaMontosDto validarStockYCalcularMontos(VentaRequest venta) {

    BigDecimal subtotalCalculado = BigDecimal.ZERO;

    for (ProductoParaVender p : venta.getProductos()) {

      ProductoStock productoStock = productoStockRepo.buscar(p.getIdProducto(), venta.getIdSucursal())
              .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

      if(productoStock.sinStock()){
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
  public List<EnumDto> tipoDocumento() {
    return Arrays.stream(TipoDocumento.values())
            .map(td -> EnumDto.builder()
                    .label(td.getLabel())
                    .value(td.getValue())
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


}
