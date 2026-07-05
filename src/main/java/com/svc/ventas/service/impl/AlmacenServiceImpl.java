package com.svc.ventas.service.impl;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.request.PresentacionUpdateRequest;
import com.svc.ventas.message.response.ProductoStockSearchResponse;
import com.svc.ventas.message.response.ResumenProductoResponse;
import com.svc.ventas.models.dao.ProductoStockPresentacionRepo;
import com.svc.ventas.models.dao.ProductoStockRepo;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.entity.ProductoStockPresentacion;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoStockPresentacionDto;
import com.svc.ventas.models.mapstruct.mappers.ProductoStockMapper;
import com.svc.ventas.models.specifications.ProductStockSpecifications;
import com.svc.ventas.service.IAlmacenService;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlmacenServiceImpl implements IAlmacenService {

  private final ProductoStockRepo productoStockRepo;

  private final ProductoStockPresentacionRepo presentacionRepo;

  private final ProductoStockMapper productoStockMapper;

  private final AppContext appContext;

  @Override
  public Map<String, Object> searchProductos(String nombre, Integer categoriaId,
                                             Boolean estado, int page, int size) {

    Specification<ProductoStock> spec = Specification.where(null);

    Long idSucursal = appContext.getSucursalId();

    if (Objects.nonNull(nombre) && !nombre.isEmpty()) {
      spec = spec.and(ProductStockSpecifications.hasName(nombre));
    }

    if (Objects.nonNull(categoriaId)) {
      spec = spec.and(ProductStockSpecifications.hasCategory(categoriaId));
    }

    if (Objects.nonNull(estado)) {
      spec = spec.and(ProductStockSpecifications.hasStatus(estado));
    }

    spec = spec.and(ProductStockSpecifications.hasSucursal(idSucursal));
    spec = spec.and(ProductStockSpecifications.hasSucursal(idSucursal));

    Pageable pageable = PageRequest.of(page, size);

    Page<ProductoStock> pageProductos = productoStockRepo.findAll(spec, pageable);

    List<ProductoStockSearchResponse> listProducts = pageProductos.getContent()
            .stream()
            .map(productoStockMapper::mapProductoSearch)
            .toList();

    ResumenProductoResponse resumen = productoStockRepo.obtenerResumen(idSucursal);

    return Map.of(
            "resumen", resumen,
            "products", listProducts,
            "currentPage", pageProductos.getNumber(),
            "pageSize", pageProductos.getSize(),
            "totalItems", pageProductos.getTotalElements(),
            "totalPages", pageProductos.getTotalPages(),
            "empty", pageProductos.isEmpty()
    );
  }

  @Override
  public ProductoStockDetailsDTO details(Long idProductoStock) {
    return productoStockRepo.findById(idProductoStock)
            .map(productoStockMapper::toProductoDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "ProductoStock", idProductoStock)));
  }

  @Override
  public List<ProductoStockPresentacionDto> presentacionesPorProductoStock(Long idProductoStock) {
    ProductoStock productoStock = productoStockRepo.findById(idProductoStock)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "ProductoStock", idProductoStock)));

    return productoStock.getPresentaciones().stream()
            .map(presentacion -> mapToProductoStockPresentacion(presentacion)
                    .build()).toList();
  }

  @Override
  public void updatePrecioVentaPresentaciones(List<PresentacionUpdateRequest> presentaciones) {
    log.info("Se inicia actualizacion de precio venta ::");

    List<ProductoStockPresentacion> list = presentaciones.stream()
            .map(presentacionRequest -> {
              ProductoStockPresentacion psp = presentacionRepo.findById(presentacionRequest.getIdPresentacion())
                      .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Presentacion", presentacionRequest.getIdPresentacion())));

              psp.setPrecioVenta(presentacionRequest.getPrecioVenta());
              psp.setPrecioSugerido(presentacionRequest.getPrecioSugerido());
              psp.setEstado(presentacionRequest.getEstado());
              return psp;
            }).toList();

    presentacionRepo.saveAll(list);

  }

  public void updateEstado(Long idProducto) {
    productoStockRepo.findById(idProducto)
            .map(p -> {
              Boolean estado = !p.getEstado();
              p.setEstado(estado);
              return productoStockRepo.save(p);
            }).orElseThrow(() -> new EntityNotFoundException
                    (String.format(Constantes.MENSAJE_NOT_FOUND, "Producto", idProducto)));
  }

  private static ProductoStockPresentacionDto.ProductoStockPresentacionDtoBuilder mapToProductoStockPresentacion(ProductoStockPresentacion presentacion) {
    return ProductoStockPresentacionDto.builder()
            .idPresentacion(presentacion.getIdPresentacion())
            .idProductoStock(presentacion.getProductoStock().getIdProductoStock())
            .presentacion(presentacion.getNombre())
            .precioSugerido(presentacion.getPrecioSugerido())
            .equivalencia(presentacion.getEquivalencia())
            .precioVenta(presentacion.getPrecioVenta())
            .estado(presentacion.getEstado())
            .precioSugerido(presentacion.getPrecioSugerido());
  }

}
