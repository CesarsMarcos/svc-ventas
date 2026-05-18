package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.message.response.ProductoSearchParaVenderResponse;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.message.response.SearchProductoCompra;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.mapstruct.dto.*;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

  @Mapping(target = "idProducto", ignore = true)
  @Mapping(target = "descripcion", source = "producto.descripcion")
  @Mapping(target = "nombre", source = "producto.nombre")
  @Mapping(target = "categoria", source = "categoria")
  @Mapping(target = "unidadMedida", source = "unidadMedida")
  @Mapping(target = "indEstado", constant = "true")
  @Mapping(target = "empresa", source = "empresa")
  @Mapping(target = "manejaPresentaciones", source = "producto.manejaPresentaciones")
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  Producto mapToProducto(ProductoRequest producto,
                         Marca marca,
                         Categoria categoria,
                         UnidadMedida unidadMedida,
                         Empresa empresa);

  Producto map(ProductoDTO dto);


  ProductoDTO map(Producto producto);

  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "codigo", source = "producto.codigo")
  @Mapping(target = "categoria", source = "producto.categoria.desCategoria")
  @Mapping(target = "marca", source = "producto.marca.descripcion")
  @Mapping(target = "unidadMedida", source = "producto.unidadMedida.nombre")
  @Mapping(target = "descripcion", source = "producto.descripcion")
  @Mapping(target = "nombre", source = "producto.nombre")
  @Mapping(target = "imagen", source = "producto.imagen")
  //@Mapping(target = "precioBase", source = "producto.precioBase")
  @Mapping(target = "estado", source = "producto.indEstado")
  @Mapping(target = "presentaciones", expression = "java(getPresentaciones(producto))")
  ProductoDetailsDTO mapDetails(Producto producto);

  ProductoDTO mapToGet(ProductoDTO productoGetDto);

  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "categoria", source = "producto.categoria.desCategoria")
  @Mapping(target = "marca", source = "producto.marca.descripcion")
  @Mapping(target = "nombre", source = "producto.nombre")
  @Mapping(target = "imagen", source = "producto.imagen")
  @Mapping(target = "estado", source = "producto.indEstado")
  ProductoSearchResponse mapToSearch(ProductoStock producto);

  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "nombre", source = "producto.nombre")
  //@Mapping(target = "precioVenta", source = "producto.precioVenta")
  @Mapping(target = "stock", source = "producto.stock")
  ProductoSearchParaVenderResponse mapToSearchProdStock(ProductoStock producto);


  @Mapping(target = "idProducto", source = "producto.idProducto")
  @Mapping(target = "codigo", source = "producto.codigo")
  @Mapping(target = "categoria", source = "producto.categoria.desCategoria")
  @Mapping(target = "marca", source = "producto.marca.descripcion")
  @Mapping(target = "nombre", source = "producto.nombre")
  //@Mapping(target = "precioBase", source = "producto.precioBase")
  @Mapping(target = "imagen", source = "producto.imagen")
  @Mapping(target = "estado", source = "producto.indEstado")
  ProductoSearchResponse mapProductoSearch(Producto producto);


  @Mapping(target = "idProducto", source = "producto.producto.idProducto")
  @Mapping(target = "nombre", source = "producto.producto.nombre")
  //@Mapping(target = "precioVenta", source = "producto.precioVenta")
  @Mapping(target = "imagen", source = "producto.producto.imagen")
  SearchProductoCompra mapSearchCompra(ProductoStock producto);


  default List<ProductoPresentacion> getPresentaciones(Producto producto) {
    if (Objects.isNull(producto.getPresentaciones())) {
      return List.of();
    }

    return producto.getPresentaciones()
            .stream().map(x -> ProductoPresentacion.builder()
                    .nombre(x.getNombre())
                    .equivalencia(x.getEquivalencia())
                    .idPresentacion(x.getIdPresentacion())
                    .isPrincipal(x.getIsPrincipal())
                    .estado(x.getEstado())
                    .build()).collect(Collectors.toList());

  }

}
