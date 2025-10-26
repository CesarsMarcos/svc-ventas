package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.models.mapstruct.dto.ProductoDTO;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Producto;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductoMapper {

	@Mapping(target = "idProducto", ignore = true)
	@Mapping(target = "indEstado", constant = "true")
	Producto mapToProducto (ProductoDTO productoDto);

	ProductoDTO map(Producto producto);

	ProductoDTO mapToGet (ProductoDTO productoGetDto);

	ProductoSearchResponse mapToSearch(Producto producto);

}
