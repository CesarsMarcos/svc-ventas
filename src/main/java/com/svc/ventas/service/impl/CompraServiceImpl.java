package com.svc.ventas.service.impl;

import static com.svc.ventas.util.Constantes.IGV;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.request.CompraRequest;
import com.svc.ventas.message.request.ProductoParaComprar;
import com.svc.ventas.message.response.Response;
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
import com.svc.ventas.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

  private final IProductoService productoService;

  private final CompraMapper compraMapper;

  private final SecurityUtils securityUtils;

  @Transactional
  @Override
  public Response registrar(CompraRequest compra) {

    log.info("Iniciando registro de compra...");

    log.info("Valida montos ::");
    CompraMontosDto compraMontosDto = validarYCalcularMontos(compra);

    log.info("Valida si ya se registro documento ::");
    valiaRegistroDocumento(compra);

    log.info("Busca proveedor :: ");
    Proveedor proveedorBD = proveedorRepo.findById(compra.getIdProveedor())
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                    Constantes.MENSAJE_NOT_FOUND, "Proveedor", compra.getIdProveedor())));

    log.info("Busca sucursal existente ::");
    Sucursal sucursalBD = sucursalRepo.findById(compra.getIdSucursal())
            .orElseThrow(() -> new EntityNotFoundException(String.format(
                    Constantes.MENSAJE_NOT_FOUND, "Sucursal", compra.getIdSucursal())));

    log.info("Obtiene usuario logueado :: ");
    Usuario usuarioLogueado = securityUtils.obtenerUsuarioLogueado();

    log.info("Registra los datos del comprobante :: ");

    Compra compraNew = Compra.builder()
            .fecha(AppUtils.convert(compra.getFecha()))
            .serie(compra.getSerie())
            .correlativo(compra.getCorrelativo())
            .tipoDocumento(compra.getTipoDocumento())
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
              log.info("Busca producto ::");
              ProductoDTO productoBD = productoService.obtener(ppc.getIdProducto());

              log.info("Busca stock de producto en sucursal ::");
              ProductoStock productoStock = productoStockRepo.buscar(ppc.getIdProducto(), compra.getIdSucursal())
                      .orElseThrow(() -> new EntityNotFoundException(":: No existe producto registrado"));

              log.info("Aumenta existencia para producto :: {} ", productoBD.getNombre());
              productoStock.sumarStock(ppc.getCantidad());

              log.info("Actualiza stock de producto :: {}, en local {} ", productoBD.getNombre(),
                      productoStock.getSucursal().getCodigo());

              productoStockRepo.save(productoStock);

              productoCompradoRepo.save(ProductoComprado
                      .builder()
                      .compra(compraEntity)
                      .idProducto(productoBD.getIdProducto())
                      .nombre(productoBD.getNombre())
                      .cantidad(ppc.getCantidad())
                      .cantidadRecibida(ppc.getCantidadRecibida())
                      .precioCompra(ppc.getPrecioCompra())
                      .subTotal(ppc.getPrecioCompra().multiply(BigDecimal.valueOf(ppc.getCantidad())))
                      .build());
            });

    String numeroDocumento = compra.getSerie() + "-" + compra.getCorrelativo();
    log.info("Compra registrada correctamente con número {}", numeroDocumento);

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();

  }

  @Override
  public Map<String, Object> searchCompras(String ruc, String proveedor,
                                           String documentoCompra, LocalDate inicio,
                                           LocalDate fin, Pageable pageable) {
    Specification<Compra> spec = Specification
            .where(CompraSpecifications.hasRUC(ruc))
            .and(CompraSpecifications.hasProveedor(proveedor))
            .and(CompraSpecifications.hasDocumento(documentoCompra))
            .and(CompraSpecifications.hasFechaBetween(inicio, fin));

    Page<Compra> pageCompra = compraRepo.findAll(spec, pageable);
    List<CompraGetDto> compraDto = pageCompra.getContent()
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
  public Object details(Long id) {
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

  private CompraMontosDto validarYCalcularMontos(CompraRequest compra) {

    BigDecimal subtotalCalculado = BigDecimal.ZERO;

    for (ProductoParaComprar p : compra.getProductos()) {

      if (Objects.isNull(p.getCantidad()) || p.getCantidad() <= 0) {
        throw new IllegalArgumentException("Cantidad inválida para el producto ID: " + p.getIdProducto());
      }

      BigDecimal subtotalProducto = p.getPrecioCompra()
              .multiply(BigDecimal.valueOf(p.getCantidad()));

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
      throw new IllegalArgumentException("El subtotal no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(compra.getIgv()) ||
            compra.getIgv().setScale(2, RoundingMode.HALF_UP).compareTo(igvCalculado) != 0) {
      log.info("IGV servidor: {}", igvCalculado);
      throw new IllegalArgumentException("El IGV no coincide con el cálculo del servidor.");
    }

    if (Objects.isNull(compra.getTotal()) ||
            compra.getTotal().setScale(2, RoundingMode.HALF_UP).compareTo(totalCalculado) != 0) {
      log.info("total servidor: {}", totalCalculado);
      throw new IllegalArgumentException("El total no coincide con el cálculo del servidor.");
    }

    log.info("Montos validados correctamente: Subtotal={}, IGV={}, Total={}",
            subtotalCalculado, igvCalculado, totalCalculado);

    return new CompraMontosDto(subtotalCalculado, igvCalculado, totalCalculado);
  }

  private void valiaRegistroDocumento(CompraRequest compra){
    if(compraRepo.existsBySerieAndCorrelativoAndSucursalIdSucursal(compra.getSerie(), compra.getCorrelativo(), compra.getIdSucursal())){
      throw new BusinessException("El numero de serie y documento ya fue registrado");
    }
  }
}
