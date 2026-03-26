package com.svc.ventas.service.impl;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.message.response.ProductoStockSearchResponse;
import com.svc.ventas.models.dao.ProductoStockRepo;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.entity.Sucursal;
import com.svc.ventas.models.entity.Usuario;
import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlmacenServiceImpl implements IAlmacenService {

  private final ProductoStockRepo productoStockRepo;

  private final ProductoStockMapper productoStockMapper;

  private final AppContext appContext;

  @Override
  public Map<String, Object> searchProductos(String nombre, Integer categoriaId, Boolean estado, int page, int size) {

    Specification<ProductoStock> spec = Specification.where(null);

    log.info("Obtiene usuario en sessión ::");
    Sucursal sucursal = appContext.getSucursal();

    if(Objects.nonNull(nombre) && !nombre.isEmpty()) {
      spec = spec.and(ProductStockSpecifications.hasName(nombre));
    }

    if(Objects.nonNull(categoriaId)){
      spec = spec.and(ProductStockSpecifications.hasCategory(categoriaId));
    }

    if(Objects.nonNull(estado)){
      spec = spec.and(ProductStockSpecifications.hasStatus(estado));
    }

    spec = spec.and(ProductStockSpecifications.hasSucursal(sucursal));

    Pageable pageable = PageRequest.of(page, size);

    Page<ProductoStock> pageProductos = productoStockRepo.findAll(spec, pageable);

    List<ProductoStockSearchResponse> listProducts = pageProductos.getContent()
            .stream()
            .map(productoStockMapper::mapProductoSearch)
            .toList();

    /*BigDecimal costoTotal =  pageProductos.getContent()
            .stream().map(ProductoStock::getPrecioVenta)
            .reduce(BigDecimal.ZERO, BigDecimal::add);*/

    return Map.of(
            "products", listProducts,
            "currentPage", pageProductos.getNumber(),
            "pageSize", pageProductos.getSize(),
            "totalItems", pageProductos.getTotalElements(),
            "totalPages", pageProductos.getTotalPages(),
            "empty", pageProductos.isEmpty()
    );
  }

  @Override
  public Map<String, Object> searchProductosVenta(String codigo, String nombre) {

    Specification<ProductoStock> spec = Specification.where(null);

    if(Objects.nonNull(codigo) && !nombre.isEmpty()) {
      spec = spec.and(ProductStockSpecifications.hasCodigo(codigo));
    }

    if(Objects.nonNull(nombre) && !nombre.isEmpty()) {
      spec = spec.and(ProductStockSpecifications.hasName(nombre));
    }

    Pageable pageable = PageRequest.of(0, 5);

    Page<ProductoStock> pageProductos = productoStockRepo.findAll(spec, pageable);

    List<ProductoStockSearchResponse> listProducts = pageProductos.getContent()
            .stream()
            .map(productoStockMapper::mapProductoSearch)
            .toList();

    return Map.of(
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
  public void updatePrecioVenta(Long idProductoStock, BigDecimal precioVenta) {
    log.info("Se inicia actualizacion de precio venta ::");
    productoStockRepo.findById(idProductoStock)
            .map(stock -> {
              stock.setPrecioVenta(precioVenta);
              return productoStockRepo.save(stock);
            }).orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "ProductoStock", idProductoStock)));
  }

}
