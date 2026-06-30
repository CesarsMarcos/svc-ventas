package com.svc.ventas.service.impl;

import static com.svc.ventas.util.Constantes.IGV;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.exception.ValidationException;
import com.svc.ventas.message.request.CompraRequest;
import com.svc.ventas.message.request.ProductoParaComprar;
import com.svc.ventas.message.response.ResponseTransaccion;
import com.svc.ventas.message.response.SearchCompraResponse;
import com.svc.ventas.models.dao.*;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.enums.EstadoCompra;
import com.svc.ventas.models.enums.TipoPagoCompra;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.models.specifications.CompraSpecifications;
import com.svc.ventas.service.*;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements ICompraService {

  private final ProductoCompradoRepository productoCompradoRepo;

  private final CompraRepository compraRepo;

  private final ProductoStockRepo productoStockRepo;

  private final ProveedorRepo proveedorRepo;

  private final SucursalRepo sucursalRepo;

  private final TipoDocumentoRepository tipoDocumentoRepo;

  private final ProductoStockPresentacionRepo presentacionRepo;

  private final CompraMapper compraMapper;

  private final AppContext appContext;

  @Transactional
  @Override
  public ResponseTransaccion registrar(CompraRequest compra) {

    log.info("Iniciando registro de compra...");

    log.info("Obtiene usuario logueado ::");
    Usuario usuarioLogueado = appContext.getUsuario();
    Sucursal sucursal = appContext.getSucursal();

    log.info("Valida montos ::");
    CompraMontosDto compraMontosDto = validarYCalcularMontos(compra);

    log.info("Obtiene tipo de documento");
    TipoDocumento tipoDocumento = tipoDocumentoRepo.findById(compra.getIdTipoDocumento())
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format(Constantes.MENSAJE_NOT_FOUND, "Tipo Documento", compra.getIdTipoDocumento())));

    log.info("Valida si ya se registro documento ::");
    valiaRegistroDocumento(compra, sucursal);

    log.info("Busca proveedor :: ");
    Proveedor proveedorBD = proveedorRepo.findById(compra.getIdProveedor())
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                    Constantes.MENSAJE_NOT_FOUND, "Proveedor", compra.getIdProveedor())));

    log.info("Busca sucursal existente ::");
    Sucursal sucursalBD = sucursalRepo.findById(sucursal.getIdSucursal())
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                    Constantes.MENSAJE_NOT_FOUND, "Sucursal", sucursal.getIdSucursal())));

    log.info("Registra los datos del comprobante :: ");

    Compra compraNew = Compra.builder()
            .fecha(AppUtils.convert(compra.getFecha()))
            .serie(compra.getSerie())
            .correlativo(compra.getCorrelativo())
            .tipoDocumento(tipoDocumento)
            .proveedor(proveedorBD)
            .sucursal(sucursalBD)
            .tipoPago(compra.getTipoPago())
            .igv(compraMontosDto.getIgv())
            .subTotal(compraMontosDto.getSubTotal())
            .total(compraMontosDto.getTotal())
            .estado(EstadoCompra.CREADO)
            .createdBy(usuarioLogueado.getUsuario())
            .build();

    Compra compraEntity = compraRepo.save(compraNew);
    log.info("Compra guardada con ID: {}", compraEntity.getIdCompra());

    log.info("Registra los productos a comprar :: ");
    compra.getProductos()
            .forEach(ppc -> {
              log.info("Busca producto :: {}", ppc.getNombre());

              log.info("Busca presentacion");
              ProductoStockPresentacion presentacionBD = presentacionRepo.findById(ppc.getIdPresentacion())
                      .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Presentacion", ppc.getIdPresentacion())));

              log.info("Busca stock de producto en sucursal ::");
              ProductoStock productoStock = productoStockRepo.buscar(ppc.getIdProducto(), sucursalBD.getIdSucursal())
                      .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Producto Stock", ppc.getIdProducto())));

              log.info("Aumenta existencia para producto :: {}, en local {} ", productoStock.getProducto().getNombre(), productoStock.getSucursal().getCodigo());
              productoStock.sumarStock(ppc.getCantidad().multiply(presentacionBD.getEquivalencia()));

              log.info("Actualiza costo promedio {} en producto stock {}", ppc.getPrecioCompra(), productoStock.getIdProductoStock());
              //productoStock.setCostoPromedio(ppc.getPrecioCompra());
              actualizarCostoPromedio(productoStock, ppc.getCantidad(), ppc.getPrecioCompra());

              ProductoStock productoStockSave = productoStockRepo.save(productoStock);

              productoCompradoRepo.save(ProductoComprado
                      .builder()
                      .compra(compraEntity)
                      .idProducto(productoStock.getProducto().getIdProducto())
                      .idPresentacion(ppc.getIdPresentacion())
                      .presentacion(presentacionBD.getNombre())
                      .nombre(productoStock.getProducto().getNombre())
                      .cantidad(ppc.getCantidad())
                      .cantidadRecibida(ppc.getCantidadRecibida())
                      .precioCompra(ppc.getPrecioCompra())
                      .subTotal(ppc.getPrecioCompra().multiply(ppc.getCantidad()))
                      .build());

              log.info("recalcular  precios sugeridos ::");
              actualizarPreciosDeVentaySugerido(productoStockSave);

            });

    String numeroDocumento = compra.getSerie().concat("-").concat(AppUtils.formatearSunat(compra.getCorrelativo()));

    log.info("Compra registrada correctamente con número {}", numeroDocumento);

    return ResponseTransaccion
            .builder()
            .total(compraEntity.getTotal())
            .tipoDocumento(compraEntity.getTipoDocumento().getDescripcion())
            .serieCorrelativo(numeroDocumento)
            .tipoPago(compraEntity.getTipoPago().getLabel())
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public Map<String, Object> searchCompras(String ruc, String proveedor,
                                           Long documentoCompra, LocalDate inicio,
                                           LocalDate fin, Pageable pageable) {

    Sucursal sucursal = appContext.getSucursal();

    Specification<Compra> spec = Specification
            .where(CompraSpecifications.hasRUC(ruc))
            .and(CompraSpecifications.hasProveedor(proveedor))
            .and(CompraSpecifications.hasSucursal(sucursal))
            .and(CompraSpecifications.hasDocumento(documentoCompra))
            .and(CompraSpecifications.hasFechaBetween(inicio, fin))
            .and(CompraSpecifications.filtroSeguridad(appContext));

    Page<Compra> pageCompra = compraRepo.findAll(spec, pageable);

    List<SearchCompraResponse> compraDto = pageCompra.getContent()
            .stream().map(compraMapper::mapCompraToDto)
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("compras", compraDto);
    response.put("currentPage", pageCompra.getNumber());
    response.put("totalItems", pageCompra.getTotalElements());
    response.put("totalPages", pageCompra.getTotalPages());

    return response;

  }

  @Override
  public CompraDetailDto details(Long id) {
    return compraRepo.findById(id)
            .map(compraMapper::mapCompraToDetailDto)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Compra", id)));

  }

  @Override
  public List<EnumDto> tipoPagoCompra() {
    return Arrays.stream(TipoPagoCompra.values())
            .filter(TipoPagoCompra::getEstado)
            .map(tpc -> EnumDto.builder()
                    .value(tpc.getValue())
                    .label(tpc.getLabel())
                    .build())
            .toList();
  }

  @Override
  public List<ProductoSearchCompraDto> searchProductosParaCompra(String filtro) {

    if (Objects.isNull(filtro) || filtro.trim().isEmpty()) {
      return Collections.emptyList();
    }

    Long idSucursal = appContext.getSucursalId();
    return compraRepo.buscarPorNombreOCodigoPresentaciones(filtro, idSucursal);
  }

  //esto va a llamar v3
  @Override
  public Map<String, Object> searchProductosParaCompraPage(String nombre, int page, int size) {
    String filtro = (nombre != null && !nombre.isBlank()) ? nombre.trim().toLowerCase() : "";

    log.info("Se obtiene usuario logueado...");
    Long idSucursal = appContext.getSucursalId();

    Pageable pageable = PageRequest.of(page, size);

    Page<ProductoSearchCompraDto> pageProductos = compraRepo.buscarPorNombreOCodigoPresentacionesPage(filtro, idSucursal, pageable);

    return Map.of(
            "products", pageProductos.getContent(),
            "currentPage", pageProductos.getNumber(),
            "pageSize", pageProductos.getSize(),
            "totalItems", pageProductos.getTotalElements(),
            "totalPages", pageProductos.getTotalPages(),
            "empty", pageProductos.isEmpty()
    );
  }

  @Override
  public List<PresentacionesCompraDto> presentacionesPorIdProducto(Long idProducto) {
    return productoStockRepo.presentacionesPorIdProducto(idProducto);
  }

  @Transactional
  public void actualizarPreciosDeVentaySugerido(ProductoStock productoStock) {

    BigDecimal costo = productoStock.getCostoPromedio();

    if (Objects.isNull(costo) || costo.compareTo(BigDecimal.ZERO) <= 0) {
      return;
    }

    BigDecimal margen = Objects.isNull(appContext.getSucursal().getMargen())
            ? appContext.getSucursal().getMargen() : appContext.getEmpresa().getMargenDefault();

    List<ProductoStockPresentacion> lista = Optional.ofNullable(
                    presentacionRepo.findByProductoStock(productoStock))
            .orElse(List.of());

    lista.forEach(psp -> {
      BigDecimal equivalencia = psp.getEquivalencia();

      BigDecimal costoTotal = costo.multiply(equivalencia);

      BigDecimal sugerido = costoTotal.multiply(BigDecimal.ONE.add(margen));

      psp.setPrecioSugerido(sugerido.setScale(2, RoundingMode.HALF_UP));
      if (psp.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0) {
        psp.setPrecioVenta(sugerido.setScale(2, RoundingMode.HALF_UP));
      }
    });

    presentacionRepo.saveAll(lista);
  }

  private void actualizarCostoPromedio(ProductoStock ps, BigDecimal cantidadCompra,
                                       BigDecimal precioCompra) {

    if (Objects.isNull(cantidadCompra) || cantidadCompra.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException("Cantidad de compra inválida");
    }

    if (Objects.isNull(precioCompra) || precioCompra.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException("Precio de compra inválido");
    }

    BigDecimal stockActual = Objects.nonNull(ps.getStock()) ? ps.getStock() : BigDecimal.ZERO;
    BigDecimal costoActual = Objects.nonNull(ps.getCostoPromedio()) ? ps.getCostoPromedio() : BigDecimal.ZERO;

    if (stockActual.compareTo(BigDecimal.ZERO) == 0 ||
            costoActual.compareTo(BigDecimal.ZERO) == 0) {

      ps.setCostoPromedio(precioCompra);
      return;
    }

    BigDecimal totalActual = stockActual.multiply(costoActual);
    BigDecimal totalCompra = cantidadCompra.multiply(precioCompra);

    BigDecimal nuevoStock = stockActual.add(cantidadCompra);

    BigDecimal nuevoCosto = totalActual.add(totalCompra)
            .divide(nuevoStock, 4, RoundingMode.HALF_UP);

    ps.setCostoPromedio(nuevoCosto);
  }

  private CompraMontosDto validarYCalcularMontos(CompraRequest compra) {

    BigDecimal subtotalCalculado = BigDecimal.ZERO;

    for (ProductoParaComprar p : compra.getProductos()) {

      if (Objects.isNull(p.getCantidad()) || p.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValidationException("Cantidad inválida para el producto ID: " + p.getIdProducto());
      }

      BigDecimal subtotalProducto = p.getPrecioCompra()
              .multiply(p.getCantidad());

      subtotalCalculado = subtotalCalculado.add(subtotalProducto);
    }

    BigDecimal igvCalculado;
    BigDecimal totalCalculado;

    //if (Boolean.TRUE.equals(compra.getAplicarImpuesto())) {
    igvCalculado = subtotalCalculado.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
    totalCalculado = subtotalCalculado.add(igvCalculado);
    //}

    if (Objects.isNull(compra.getSubTotal()) ||
            compra.getSubTotal().setScale(2, RoundingMode.HALF_UP).compareTo(subtotalCalculado) != 0) {
      log.info("subtotal servidor: {}", subtotalCalculado);
      throw new ValidationException("El subtotal no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(compra.getIgv()) ||
            compra.getIgv().setScale(2, RoundingMode.HALF_UP).compareTo(igvCalculado) != 0) {
      log.info("IGV servidor: {}", igvCalculado);
      throw new ValidationException("El IGV no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(compra.getTotal()) ||
            compra.getTotal().setScale(2, RoundingMode.HALF_UP).compareTo(totalCalculado) != 0) {
      log.info("total servidor: {}", totalCalculado);
      throw new ValidationException("El total no coincide con el cálculo del servidor.");
    }

    log.info("Montos validados correctamente: Subtotal={}, IGV={}, Total={}",
            subtotalCalculado, igvCalculado, totalCalculado);

    return new CompraMontosDto(subtotalCalculado, igvCalculado, totalCalculado);
  }

  private void valiaRegistroDocumento(CompraRequest compra, Sucursal sucursal) {
    if (compraRepo.existsBySerieAndCorrelativoAndSucursal(compra.getSerie(), compra.getCorrelativo(), sucursal)) {
      throw new BusinessException("El número de serie y documento ya fue registrado");
    }
  }
}
