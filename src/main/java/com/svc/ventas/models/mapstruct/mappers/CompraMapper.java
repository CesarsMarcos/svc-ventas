package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.response.SearchCompraResponse;
import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.mapstruct.dto.CompraDetailDto;
import com.svc.ventas.models.mapstruct.dto.ProductoDetalleCompraDto;
import com.svc.ventas.util.AppUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = AppUtils.class)
public interface CompraMapper {

    CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    @Mapping(target = "id", source = "idCompra")
    @Mapping(source = "estado",  target = "estado")
    @Mapping(source = "tipoDocumento.descripcion",  target = "tipoDocumento")
    @Mapping(target = "proveedor", source = "proveedor.razonSocial")
    SearchCompraResponse mapCompraToDto(Compra compra);

    @Mapping(target = "id", source = "idCompra")
    @Mapping(target = "proveedor", source = "proveedor.razonSocial")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento.descripcion")
    @Mapping(target = "tipoPago", source = "tipoPago")
    @Mapping(target = "serieCorrelativo",expression = "java(mapCorrelativo(compra))")
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
                        .presentacion(p.getPresentacion())
                        .descripcion(p.getDescripcion())
                        .precioCompra(p.getPrecioCompra())
                        .cantidadRecibida(p.getCantidadRecibida())
                        .cantidad(p.getCantidad())
                        .subTotal(p.getSubTotal())
                        .build()).collect(Collectors.toList());
    }

    default String mapCorrelativo(Compra compra){
        return compra.getSerie().concat("-").concat(AppUtils.formatearSunat(compra.getCorrelativo()));
    }

}
