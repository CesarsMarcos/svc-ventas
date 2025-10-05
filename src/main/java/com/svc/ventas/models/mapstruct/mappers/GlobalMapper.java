package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.mapstruct.dto.EmpresaGetDto;
import com.svc.ventas.models.mapstruct.dto.EmpresaPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "indEstado", constant = "true")
    Empresa mapToEntity(EmpresaPostDto empresa);

    EmpresaPostDto mapToDto (Empresa empresa);

    EmpresaGetDto mapToGetDto (Empresa empresa);

}
