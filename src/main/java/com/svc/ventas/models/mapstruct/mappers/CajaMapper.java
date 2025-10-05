package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Caja;
import com.svc.ventas.models.entity.CajaMovimiento;
import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.CajaDTO;
import com.svc.ventas.models.mapstruct.dto.CajaDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.CajaMovimientosDTO;
import com.svc.ventas.util.AppUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", imports = {AppUtils.class})
public interface CajaMapper {

    CajaMapper INSTANCE = Mappers.getMapper(CajaMapper.class);

    @Mapping(target = "fecha", expression = "java(AppUtils.obtenerFechaActual())")
    @Mapping(target = "horaApertura", expression = "java(AppUtils.obtenerHoraActual())")
    Caja toEntity (CajaDTO cajaDTO);

    @Mapping(source = "usuario.empleado.persona.nombre", target = "usuario")
    @Mapping(target = "movimiento.ingresos", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"INGRESO\"))")
    @Mapping(target = "movimiento.devoluciones", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"DEVOLUCIONES\"))")
    @Mapping(target = "movimiento.salidas", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"GASTOS\"))")
    @Mapping(target = "movimiento.prestamos", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"PRESTAMOS\"))")
    CajaDetalleDTO toModelDto (Caja caja);

    default  List<CajaMovimientosDTO> filtrarPorTipoMovimiento(List<CajaMovimiento> movimientos, String tipo){
        if (movimientos == null) return List.of();

        return movimientos.stream()
                .filter(m -> tipo.equalsIgnoreCase(m.getTipoMovimiento().name()))
                .map(this::mapToDTO)
                .toList();
    }

    default CajaMovimientosDTO mapToDTO(CajaMovimiento movimiento) {
        if (movimiento == null) return null;

        CajaMovimientosDTO dto = new CajaMovimientosDTO();
        dto.setIdCajaMovimiento(movimiento.getIdCajaMovimiento());
        dto.setTipoMovimiento(TipoMovimiento.valueOf(movimiento.getTipoMovimiento().name()));
        dto.setDocumento(movimiento.getDocumento());
        String tipoPagoStr = movimiento.getTipoPago().name();
        if (!tipoPagoStr.trim().isEmpty()) {
            dto.setTipoPago(TipoPago.valueOf(tipoPagoStr));
        } else {
            dto.setTipoPago(null);
        }
        dto.setMonto(movimiento.getMonto());
        dto.setDescripcion(movimiento.getDescripcion());
        return dto;
    }

}
