package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.CajaMovimiento;
import com.svc.ventas.models.mapstruct.dto.CajaMovimientosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    CajaMovimiento toEntity (CajaMovimientosDTO movimientosDTO);

    CajaMovimientosDTO toModelDto (CajaMovimiento cajaMovimiento);

}
