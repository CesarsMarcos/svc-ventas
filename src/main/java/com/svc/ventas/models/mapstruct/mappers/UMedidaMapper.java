package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.UnidadMedida;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UMedidaMapper {

    @Mapping(target = "idUmedida", ignore = true)
    UnidadMedida mapToUnidadMedida (UnidadMedidaDto unidadMedidaGetDto);

}
