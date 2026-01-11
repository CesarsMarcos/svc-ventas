package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.mapstruct.dto.CompraDetailDto;
import com.svc.ventas.models.mapstruct.dto.CompraGetDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDetalleCompraDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    @Mapping(target = "id", source = "idCompra")
    @Mapping(target = "proveedor", source = "proveedor.razonSocial")
    CompraGetDto mapCompraToDto(Compra compra);

    @Mapping(target = "id", source = "idCompra")
    @Mapping(target = "proveedor", source = "proveedor.razonSocial")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento")
    @Mapping(target = "tipoPago", source = "tipoPago")
    @Mapping(target = "serieCorrelativo",expression = "java(compra.getSerie().concat(\"-\").concat(compra.getCorrelativo()))")
    @Mapping(target = "productos", expression = "java(listProductos(compra))")
    CompraDetailDto mapCompraToDetailDto(Compra compra);


    default List<ProductoDetalleCompraDto> listProductos(Compra compra) {
        if (compra.getProductos().isEmpty()) {
            return null;
        }
        return compra.getProductos().stream()
                .map(p -> ProductoDetalleCompraDto.builder()
                        .idProducto(p.getIdProducto())
                        .nombre(p.getNombre())
                        .descripcion(p.getDescripcion())
                        .precioCompra(p.getPrecioCompra())
                        .cantidadRecibida(p.getCantidadRecibida())
                        .cantidad(p.getCantidad())
                        .subTotal(p.getSubTotal())
                        .build()).collect(Collectors.toList());
    }

}
