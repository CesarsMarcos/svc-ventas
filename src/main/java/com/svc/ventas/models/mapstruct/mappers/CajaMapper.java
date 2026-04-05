package com.svc.ventas.models.mapstruct.mappers;

import com.svc.ventas.models.entity.Caja;
import com.svc.ventas.models.entity.CajaMovimiento;
import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.*;
import com.svc.ventas.util.AppUtils;
import com.svc.ventas.util.Constantes;
import com.svc.ventas.util.NumeroATexto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper(componentModel = "spring",
        imports = {AppUtils.class, Constantes.class, NumeroATexto.class, BigDecimal.class})
public interface CajaMapper {

    CajaMapper INSTANCE = Mappers.getMapper(CajaMapper.class);

    @Mapping(source = "caja.usuario.empleado.persona.nombre", target = "usuario")
    @Mapping(target = "movimiento.ingresos", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"INGRESO\"))")
    @Mapping(target = "movimiento.devoluciones", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"DEVOLUCIONES\"))")
    @Mapping(target = "movimiento.salidas", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"GASTOS\"))")
    @Mapping(target = "movimiento.prestamos", expression = "java(filtrarPorTipoMovimiento(caja.getMovimientos(), \"PRESTAMOS\"))")
    @Mapping(target = "totales", source = "totales")
    CajaDataDto toModelDto (Caja caja, TotalesCaja totales);

    @Mapping(target = "cajero", expression = "java(caja.getUsuario().getUsuario())")
    @Mapping(target = "fecApertura", source = "caja.fechaHoraApertura")
    @Mapping(target = "fecCierre", source = "caja.fechaHoraCierre")
    @Mapping(target = "estado", expression = "java(String.valueOf(caja.getEstado()))")
    @Mapping(target = "moneda", constant = "java(Constantes.MONEDA_PER)")
    @Mapping(target = "totalesPorPago", source = "totalesPorPago")
    @Mapping(target = "totalesPorMovimiento", source = "totalesPorMovimiento")
    @Mapping(target = "totalEgresos", source = "totales.totalEgresos")
    @Mapping(target = "totalIngresos", source = "totales.totalIngresos")
    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "montoInicialMasSaldo", source = "montoInicialMasSaldo")
    @Mapping(target = "totalEfectivoEnCaja", source = "finales.totalEfectivoEnCaja")
    @Mapping(target = "totalCtaBancaria", source = "finales.totalCtaBancaria")
    @Mapping(target = "totalCuadre", source = "finales.totalCuadre")
    @Mapping(target = "montoTexto", expression = "java(NumeroATexto.convertir(finales.getTotalCuadre(), Constantes.MONEDA_PER))")
    ResumenCajaDTO toResumenCajaDTO(
            Caja caja,
            Map<TipoPago, BigDecimal> totalesPorPago,
            Map<TipoMovimiento, BigDecimal> totalesPorMovimiento,
            BigDecimal saldo,
            BigDecimal montoInicialMasSaldo,
            TotalesCaja totales,
            TotalesFinales finales
    );

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
        dto.setTipoPago(movimiento.getTipoPago());
        dto.setMonto(movimiento.getMonto());
        dto.setDescripcion(movimiento.getDescripcion());
        return dto;
    }

}
