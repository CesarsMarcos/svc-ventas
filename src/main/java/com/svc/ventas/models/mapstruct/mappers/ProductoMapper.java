package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.ProductoRequest;
import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.models.entity.*;
import com.svc.ventas.models.mapstruct.dto.CategoriaDto;
import com.svc.ventas.models.mapstruct.dto.MarcaDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaDto;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductoMapper {

	@Mapping(target = "idProducto", ignore = true)
	@Mapping(target = "descripcion", source = "producto.descripcion")
	@Mapping(target = "nombre", source = "producto.nombre")
	@Mapping(target = "categoria", source = "categoria")
	@Mapping(target = "unidadMedida", source = "unidadMedida")
	@Mapping(target = "indEstado", constant = "true")
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Producto mapToProducto (ProductoRequest producto,
													Marca marca,
													Categoria categoria,
													UnidadMedida unidadMedida);

	Producto map (ProductoDTO dto);

	ProductoDTO map(Producto producto);

	@Mapping(target = "idProducto", source = "producto.idProducto")
	@Mapping(target = "categoria", source = "producto.categoria.desCategoria")
	@Mapping(target = "marca", source = "producto.marca.descripcion")
	@Mapping(target = "nombre", source = "producto.nombre")
	@Mapping(target = "precioVenta", source = "producto.precioVenta")
	@Mapping(target = "estado", source = "producto.indEstado")
	ProductoSearchResponse mapProductoStock(ProductoStock producto);

	ProductoDTO mapToGet (ProductoDTO productoGetDto);

	@Mapping(target = "categoria", source = "categoria.desCategoria")
	@Mapping(target = "marca", source = "marca.descripcion")
	ProductoSearchResponse mapToSearch(Producto producto);

}
