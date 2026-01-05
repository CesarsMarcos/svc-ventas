package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.entity.ProductoVendido;
import com.svc.ventas.models.mapstruct.dto.ClienteGetVentaDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDetalleDto;
import com.svc.ventas.models.mapstruct.dto.VentaGetDto;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Venta;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VentaMapper {

	VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

	@Mapping(source = "idVenta", target = "id" )
	@Mapping(source = "fecha",  target = "fecha")
	@Mapping(source = "estado",  target = "estado")
	@Mapping(source = "serie",  target = "serie")
	@Mapping(source = "correlativo",  target = "correlativo")
	@Mapping(target = "cliente", expression = "java(dataCliente(venta))")
	@Mapping(target = "productos",  expression = "java(mapProductosDetalle(venta.getProductos()))")
	@Mapping(source = "igv",  target = "igv")
	@Mapping(source = "subTotal",  target = "subTotal")
	@Mapping(source = "total",  target = "total")

	VentaGetDto mapToVentaGetDto(Venta venta);

	default ClienteGetVentaDto dataCliente(Venta venta) {
			Cliente cliente = venta.getCliente();
			return ClienteGetVentaDto.builder()
							.nombreCompleto(cliente.getPersona().getNombre().concat(" ")
											.concat(cliente.getPersona().getApePaterno().concat(" ")
															.concat(cliente.getPersona().getApeMaterno())))
							.numDocumento(cliente.getPersona().getNumDocumento())
							.correo(cliente.getPersona().getCorreo())
							.direccion(cliente.getPersona().getDireccion())
							.build();
	}

	default List<ProductoDetalleDto> mapProductosDetalle(Set<ProductoVendido> productos) {
		if (productos.isEmpty()) {
			return null;
		}
		return productos.stream()
						.map(producto ->
										ProductoDetalleDto.builder()
														.idProducto(producto.getIdProducto())
														.descripcion(producto.getDescripcion())
														.total(producto.getPrecio().multiply(BigDecimal.valueOf(producto.getCantidad())))
														.precioBase(producto.getPrecio())
														.cantidad(producto.getCantidad())
														.nombre(producto.getNombre())
														.build()
						).collect(Collectors.toList());
	}

}
