package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.ProductoSearchResponse;
import com.svc.ventas.models.mapstruct.dto.ProductoGetDTO;
import com.svc.ventas.models.mapstruct.dto.ProductoPostDTO;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Producto;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductoMapper {

	@Mapping(target = "indEstado", constant = "true")
	Producto mapToProducto (ProductoPostDTO productoDto);

	ProductoPostDTO mapToProductoDto (Producto producto);
	
	ProductoGetDTO map(Producto producto);

	ProductoPostDTO mapToGet (ProductoGetDTO productoGetDto);

	ProductoSearchResponse mapToSearch(Producto producto);

}
