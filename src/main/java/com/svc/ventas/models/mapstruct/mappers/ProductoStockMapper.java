package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.ProductoStockSearchResponse;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoStockPresentacionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductoStockMapper {

  @Mapping(source = "producto.idProducto", target = "idProducto")
  @Mapping(source = "producto.codigo", target = "codigo")
  @Mapping(source = "producto.nombre", target = "nombre")
  @Mapping(source = "sucursal.razonSocial", target = "sucursal")
  @Mapping(source = "producto.categoria.desCategoria", target = "categoria")
  @Mapping(source = "minCantidad", target = "minCantidad")
  @Mapping(source = "costoPromedio", target = "costoPromedio")
  @Mapping(source = "maxCantidad", target = "maxCantidad")
  @Mapping(source = "stock", target = "stock")
  @Mapping(target = "presentaciones", expression = "java(getPresentaciones(productoStock))")
  ProductoStockDetailsDTO toProductoDetails (ProductoStock productoStock);

  @Mapping(target = "idProductoStock", source = "idProductoStock")
  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "codigo", source = "producto.codigo")
  @Mapping(target = "categoria", source = "producto.categoria.desCategoria")
  @Mapping(target = "marca", source = "producto.marca.descripcion")
  @Mapping(target = "sucursal", source = "sucursal.razonSocial")
  @Mapping(target = "nombre", source = "producto.nombre")
  @Mapping(target = "stock", source = "producto.stock")
  @Mapping(target = "costoPromedio", source = "producto.costoPromedio")
  @Mapping(target = "minCantidad", source = "producto.minCantidad")
  @Mapping(target = "maxCantidad", source = "producto.maxCantidad")
  @Mapping(target = "estado", source = "estado")
  ProductoStockSearchResponse mapProductoSearch(ProductoStock producto);


  default List<ProductoStockPresentacionDto> getPresentaciones(ProductoStock producto) {
    if (Objects.isNull(producto.getPresentaciones())) {
      return List.of();
    }

    return producto.getPresentaciones()
            .stream().map(p -> ProductoStockPresentacionDto.builder()
                    .presentacion(p.getNombre())
                    .equivalencia(p.getEquivalencia())
                    .precioSugerido(p.getPrecioSugerido())
                    .precioVenta(p.getPrecioVenta())
                    .idPresentacion(p.getIdPresentacion())
                    .estado(p.getEstado())
                    .build()).collect(Collectors.toList());

  }

}

