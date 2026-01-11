package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.mapstruct.dto.*;
import org.mapstruct.Mapper;

import com.svc.ventas.models.entity.Venta;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VentaMapper {

	VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

	@Mapping(source = "idVenta", target = "id" )
	@Mapping(source = "fecha",  target = "fecha")
	@Mapping(source = "estado",  target = "estado")
	@Mapping(source = "tipoPago",  target = "tipoPago")
	@Mapping(target = "cliente", expression = "java(dataNombreCliente(venta))")
	@Mapping(source = "igv",  target = "igv")
	@Mapping(source = "subTotal",  target = "subTotal")
	@Mapping(source = "total",  target = "total")
	VentaGetDto mapToVentaGetDto(Venta venta);

	@Mapping(source = "idVenta", target = "id" )
	@Mapping(source = "fecha",  target = "fecha")
	@Mapping(source = "estado",  target = "estado")
	@Mapping(source = "tipoPago",  target = "tipoPago")
	@Mapping(target = "cliente", expression = "java(dataNombreCliente(venta))")
	@Mapping(target = "serieCorrelativo",expression = "java(venta.getSerie().concat(\"-\").concat(venta.getCorrelativo().toString()))")
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

}
