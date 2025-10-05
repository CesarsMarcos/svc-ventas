package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.UnidadMedida;
import com.svc.ventas.models.mapstruct.dto.UnidadMedidaGetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UMedidaMapper {

    UnidadMedida mapToUnidadMedida (UnidadMedidaGetDto unidadMedidaGetDto);

}
