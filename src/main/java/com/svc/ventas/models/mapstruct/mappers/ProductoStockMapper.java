package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.ProductoStockSearchResponse;
import com.svc.ventas.models.entity.ProductoStock;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoStockDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoStockMapper {

  @Mapping(source = "producto.idProducto", target = "idProducto")
  @Mapping(source = "producto.codigo", target = "codigo")
  @Mapping(source = "producto.nombre", target = "nombre")
  @Mapping(source = "sucursal.razonSocial", target = "sucursal")
  @Mapping(source = "producto.categoria.desCategoria", target = "categoria")
  @Mapping(source = "precioVenta", target = "precioVenta")
  @Mapping(source = "precioDescuento", target = "precioDescuento")
  @Mapping(source = "minCantidad", target = "minCantidad")
  @Mapping(source = "maxCantidad", target = "maxCantidad")
  @Mapping(source = "stock", target = "stock")
  ProductoStockDetailsDTO toProductoDetails (ProductoStock productoStock);

  @Mapping(source = "producto.idProducto", target = "idProducto")
  @Mapping(source = "producto.marca", target = "marca")
  @Mapping(source = "producto.nombre", target = "nombre")
  //@Mapping(source = "producto.codigo", target = "codigo")
  @Mapping(source = "producto.descripcion", target = "descripcion")
  @Mapping(source = "producto.categoria", target = "categoria")
  @Mapping(source = "producto.unidadMedida", target = "unidadMedida")
  @Mapping(source = "producto.imagen", target = "imagen")
  //@Mapping(source = "producto.precioBase", target = "precioBase")
  @Mapping(source = "producto.indEstado", target = "estado")
  //@Mapping(source = "precioVenta", target = "precioVenta")
  //@Mapping(source = "minCantidad", target = "minCantidad")
  //@Mapping(source = "maxCantidad", target = "maxCantidad")
  //@Mapping(source = "stock", target = "stock")
  ProductoDTO toProducto (ProductoStock productoStock);

  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "codigo", source = "producto.codigo")
  @Mapping(target = "categoria", source = "producto.categoria.desCategoria")
  @Mapping(target = "marca", source = "producto.marca.descripcion")
  @Mapping(target = "sucursal", source = "sucursal.razonSocial")
  @Mapping(target = "nombre", source = "producto.nombre")
  @Mapping(target = "stock", source = "producto.stock")
  @Mapping(target = "minCantidad", source = "producto.minCantidad")
  @Mapping(target = "maxCantidad", source = "producto.maxCantidad")
  @Mapping(target = "precioDescuento", source = "producto.precioDescuento")
  @Mapping(target = "precioVenta", source = "producto.precioVenta")
  @Mapping(target = "estado", source = "indEstado")
  ProductoStockSearchResponse mapProductoSearch(ProductoStock producto);

}

