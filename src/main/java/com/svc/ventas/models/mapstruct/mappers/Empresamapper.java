package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Empresamapper {

    @Mapping(target = "idEmpresa", ignore = true)
    @Mapping(target = "indEstado", constant = "true")
    Empresa mapToEntity(EmpresaDto empresa);

    EmpresaDto mapToDto (Empresa empresa);

    EmpresaDto mapToGetDto (Empresa empresa);

}
