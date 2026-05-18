package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDetalleVentaDto;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import com.svc.ventas.util.NumeroATexto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = AppUtils.class)
public interface ImpresionVentaMapper {

  @Mapping(source = "empresa.logo", target = "logo")
  @Mapping(source = "empresa.razonSocial", target = "razonSocial")
  @Mapping(source = "empresa.ruc", target = "ruc")
  @Mapping(source = "empresa.direccion", target = "direccion")
  @Mapping(source = "empresa.distrito", target = "distrito")
  @Mapping(source = "empresa.provincia", target = "provincia")
  @Mapping(source = "venta.tipoDocumento.descripcion", target = "tipoDocumento")
  @Mapping(target = "serieCorrelativo", expression = "java(mapCorrelativo(venta))")
  @Mapping(target = "fecha", expression = "java(mapFecha(venta.getFecAdd()))")
  @Mapping(source = "venta.cliente.persona.nombreMostrado", target = "nomCliente")
  @Mapping(source = "venta.cliente.persona.numDocumento", target = "numDocumento")
  @Mapping(source = "venta.tipoPago", target = "tipoPago")
  @Mapping(target = "productos", expression = "java(listProductos(venta))")
  @Mapping(source = "venta.igv", target = "igv")
  @Mapping(source = "venta.subTotal", target = "subTotal")
  @Mapping(source = "venta.total", target = "total")
  @Mapping(target = "totalTexto", expression = "java(mapTotalAPagarATexto(venta.getTotal()))")
  @Mapping(source = "venta.createdBy", target = "usuarioRegistro")
  DetalleImpresionDto toDto(Empresa empresa, Venta venta);

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

  default String mapFecha(LocalDateTime fecha) {
    return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }

  default String mapCorrelativo(Venta venta){
    return venta.getSerie().concat("-").concat(AppUtils.formatearSunat(venta.getCorrelativo()));
  }

  default String mapTotalAPagarATexto(BigDecimal totalAPagar) {
    return NumeroATexto.convertir(totalAPagar, Constantes.MONEDA_PER);
  }

}
