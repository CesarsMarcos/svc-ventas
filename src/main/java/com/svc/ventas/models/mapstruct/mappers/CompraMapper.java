package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Compra;
import com.svc.ventas.models.entity.Proveedor;
import com.svc.ventas.models.mapstruct.dto.CompraGetDto;
import com.svc.ventas.models.mapstruct.dto.ProveedorGetCompraDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    @Mapping(target = "id", source = "idCompra")
    @Mapping(target = "proveedor",  expression = "java(dataToProveedor(compra))")
    CompraGetDto mapCompraToDto(Compra compra);

    default ProveedorGetCompraDto dataToProveedor(Compra compra) {
        Proveedor proveedor = compra.getProveedor();
        return ProveedorGetCompraDto
                .builder()
                .numDocumento(proveedor.getNumDocumento())
                .razonSocial(proveedor.getRazonSocial())
                .direccion(proveedor.getDireccion())
                .correo(proveedor.getCorreo())
                .build();
    }

}
