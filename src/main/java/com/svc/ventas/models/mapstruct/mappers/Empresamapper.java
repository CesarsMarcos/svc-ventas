package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.message.request.EmpresaPostRequest;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Empresamapper {

    @Mapping(target = "idEmpresa", ignore = true)
    @Mapping(target = "indEstado", constant = "true")
    Empresa mapToEntity(EmpresaPostRequest empresa);

    @Mapping(target = "estado", source = "indEstado")
    EmpresaGetDto mapToDto (Empresa empresa);

    @Mapping(target = "margenDefault", source = "margenDefault")
    @Mapping(target = "estado", source = "indEstado")
    EmpresaGetDto mapToGetDto (Empresa empresa);

}
