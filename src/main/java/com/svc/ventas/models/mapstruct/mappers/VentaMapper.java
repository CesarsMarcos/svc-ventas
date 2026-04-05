package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.util.AppUtils;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Venta;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = AppUtils.class)
public interface VentaMapper {

	VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

	@Mapping(source = "idVenta", target = "id" )
	@Mapping(source = "fecAdd",  target = "fecha")
	@Mapping(source = "estado",  target = "estado")
	@Mapping(source = "tipoPago",  target = "tipoPago")
	@Mapping(target = "cliente", expression = "java(dataNombreCliente(venta))")
	@Mapping(source = "igv",  target = "igv")
	@Mapping(source = "subTotal",  target = "subTotal")
	@Mapping(source = "total",  target = "total")
	VentaGetDto mapToVentaGetDto(Venta venta);

	@Mapping(source = "idVenta", target = "id" )
	@Mapping(source = "fecAdd",  target = "fecha")
	@Mapping(source = "estado",  target = "estado")
	@Mapping(source = "tipoPago",  target = "tipoPago")
	@Mapping(source = "tipoDocumento.descripcion",  target = "tipoDocumento")
	@Mapping(target = "cliente", expression = "java(dataNombreCliente(venta))")
	@Mapping(target = "serieCorrelativo", expression = "java(mapCorrelativo(venta))")
	@Mapping(source = "igv",  target = "igv")
	@Mapping(source = "subTotal",  target = "subTotal")
	@Mapping(source = "total",  target = "total")
	@Mapping(target = "productos", expression = "java(listProductos(venta))")
	VentaDetailDto mapToVentaDetailDto(Venta venta);

	default String dataNombreCliente(Venta venta) {
		Cliente cliente = venta.getCliente();
		return cliente.getPersona().getNombre().concat(" ")
										.concat(cliente.getPersona().getApePaterno().concat(" ")
														.concat(cliente.getPersona().getApeMaterno()));
	}

	default List<ProductoDetalleVentaDto> listProductos(Venta venta) {
		if (venta.getProductos().isEmpty()) {
			return null;
		}
		return venta.getProductos().stream()
						.map(p -> ProductoDetalleVentaDto.builder()
										.idProducto(p.getIdProducto())
										.nombre(p.getNombre())
										.descripcion(p.getDescripcion())
										.precioVenta(p.getPrecio())
										.cantidad(p.getCantidad())
										.subTotal(p.getSubTotal())
										.build()).collect(Collectors.toList());
	}

	default String mapCorrelativo(Venta venta){
		return venta.getSerie().concat("-").concat(AppUtils.formatearSunat(venta.getCorrelativo()));
	}

}
