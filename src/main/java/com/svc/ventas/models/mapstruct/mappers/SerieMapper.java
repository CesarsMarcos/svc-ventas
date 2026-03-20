package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SerieMapper {

  @Mapping(source = "idSerie", target = "idSerie")
  @Mapping(source = "tipoDocumento.descripcion", target = "tipoDocumento")
  @Mapping(source = "serie", target = "serie")
  @Mapping(source = "correlativo", target = "correlativo")
  @Mapping(source = "sucursal.razonSocial", target = "sucursal")
  @Mapping(source = "createdBy", target = "createdBy")
  @Mapping(source = "fecAdd", target = "fecAdd")
  @Mapping(source = "indEstado", target = "estado")
  SerieDTO toEntity(Serie serie);

}
