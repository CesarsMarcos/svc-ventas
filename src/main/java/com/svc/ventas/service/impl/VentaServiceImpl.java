package com.svc.ventas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.svc.ventas.exception.BusinessException;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.request.ProductoParaVender;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.SecurityUtils;
import jakarta.transaction.Transactional;

import com.svc.ventas.models.mapstruct.mappers.*;
import com.svc.ventas.service.*;
import org.springframework.stereotype.Service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.dao.VentaRepo;
import com.svc.ventas.util.Constantes;
import com.svc.ventas.models.dao.ProductoVendidoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {

  private final IClienteService clienteService;

  private final IProductoService productoService;

  private final ITipoDocumentoService tipoDocumentoService;

  private final IUsuarioService usuarioService;

  private final ISerieService serieService;

  private final ICajaService cajaService;

  private final ProductoVendidoRepository productoVendidoRepo;

  private final VentaRepo ventaRepo;

  private final ProductoMapper productoMapper;

  private final VentaMapper ventaMapper;

  private final ClienteMapper clienteMapper;

  private final UsuarioMapper usuarioMapper;

  private final TipoDocumentoMapper tipoDocumentoMapper;

  private final SecurityUtils securityUtils;

  @Override
  @Transactional
  public Response registrar(VentaDto ventaDto) {

    log.info("Iniciando registro de venta...");

    log.info("Obtener caja activa ::");
    CajaDetalleDTO cajaDet = cajaService.findByFechaAndUsuario();

    log.info("Busca cliente :: ");
    clienteService.obtener(ventaDto.getCliente().getIdCliente());

    log.info("Busca tipo de documento existente :: ");
    tipoDocumentoService.obtener(ventaDto.getTipoDocumento().getIdTipoDocumento());

    log.info("Obtiene usuario logueado ::");
    UsuarioDto usuarioLogueado = securityUtils.obtenerUsuarioLogueado();

    log.info("Validar correlativo ::");
    Serie serieBD = serieService.getByIdDocumentType(ventaDto.getTipoDocumento().getIdTipoDocumento());
    int nextCorrelativo = serieBD.getCorrelativo() + 1;

    String numeroDocumento = serieBD.getSerie() + "-" + String.format("%05d", nextCorrelativo);
    log.info("Número de documento generado: {}", numeroDocumento);

    log.info("Actualizar correlativo en series ::");
    serieBD.setCorrelativo(nextCorrelativo);
    serieService.save(serieBD);

    Venta ventaNew = Venta.builder()
            .cliente(clienteMapper.mapCliente(ventaDto.getCliente()))
            .tipoDocumento(tipoDocumentoMapper.mapTipoDocumento(ventaDto.getTipoDocumento()))
            .tipoPago(ventaDto.getTipoPago())
            .serie(serieBD.getSerie())
            .correlativo(nextCorrelativo)
            .igv(ventaDto.getIgv())
            .subTotal(ventaDto.getSubTotal())
            .total(ventaDto.getTotal())
            .fecha(AppUtils.convert(ventaDto.getFecha()))
            .estado(Constantes.STATUS_CREADO)
            .usuRegistro(usuarioMapper.mapToUsuarioGet(usuarioLogueado))
            .build();

    Venta ventaEntity = ventaRepo.save(ventaNew);
    log.info("Venta guardada con ID: {}", ventaEntity.getIdVenta());

    log.info("Registra los productos a vender :: ");

    ventaDto.getProductos()
            .forEach(ppv -> {
              log.info("Busca producto y actualiza el stock del producto ::");
              ProductoDTO productoBD = productoService.obtener(ppv.getIdProducto());

              log.info("Valida disponibilidad de stock para producto :: {} ", productoBD.getNombre() );
              validarStock(ppv, productoBD);

              productoBD.restarStock(ppv.getCantidad());

              productoService.modificar(productoBD.getIdProducto(), productoMapper.mapToGet(productoBD));

              productoVendidoRepo.save(ProductoVendido
                      .builder()
                      .idProducto(productoBD.getIdProducto())
                      .descripcion(productoBD.getDescripcion())
                      .nombre(productoBD.getNombre())
                      .precio(productoBD.getPrecio())
                      .cantidad(ppv.getCantidad())
                      .venta(ventaEntity)
                      .build());
            });

    CajaMovimientosDTO mov = CajaMovimientosDTO
            .builder()
            .tipoMovimiento(TipoMovimiento.INGRESO)
            .documento(numeroDocumento)
            .monto(ventaNew.getTotal())
            .tipoPago(TipoPago.EFECTIVO)
            .build();
    cajaService.agregarMovimiento(cajaDet.getIdCaja(), mov);
    log.info("Venta registrada correctamente con número {}", numeroDocumento);

    return Response
            .builder()
            .mensaje(Constantes.MENSAJE_SAVE)
            .build();
  }

  @Override
  public List<VentaGetDto> listado(Boolean isViewMore) {
    LocalDate dateToday = LocalDate.now();
    LocalDate sevenDaysAgo = dateToday.minusWeeks(1);

    return ventaRepo.findAll().stream()
            .filter(compra -> {
              if (isViewMore) {
                return compra.getFecha().isAfter(sevenDaysAgo.minusDays(1)) && compra.getFecha().isBefore(dateToday.plusDays(1));
              } else {
                return compra.getFecha().isEqual(dateToday);
              }
            })
            .map(ventaMapper::mapToVentaGetDto)
            .collect(Collectors.toList());
  }

  @Override
  public Object details(Long id) {
    return ventaRepo.findById(id)
            .map(ventaMapper::mapToVentaGetDto)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Venta", id)));
  }

  @Override
  public List<Venta> listadoVentasPorCliente(String dni/*, String fecha*/) {
    return ventaRepo.ventasPorDocumentoCliente(dni);
  }

  private static void validarStock(ProductoParaVender ppv, ProductoDTO productoBD) {
    if(productoBD.sinStock()){
      throw new BusinessException("Stock insuficiente para el producto: " + productoBD.getNombre() +
              ". Disponible: " + productoBD.getStock() + ", Solicitado: " + ppv.getCantidad());
    }

    if (productoBD.getStock() < ppv.getCantidad()) {
      throw new BusinessException("Stock insuficiente para el producto: " + productoBD.getNombre() +
              ". Disponible: " + productoBD.getStock() + ", Solicitado: " + ppv.getCantidad());
    }
  }

}
