package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumenCajaDTO {

    private String cajero;
    private String fecApertura;
    private String fecCierre;
    private String estado;
    private String moneda;
    private Map<TipoPago, BigDecimal> totalesPorPago;

    private Map<TipoMovimiento, BigDecimal> totalesPorMovimiento;
    private BigDecimal totalIngresos;
    private BigDecimal totalEgresos;
    private BigDecimal saldo;
    private BigDecimal montoInicialMasSaldo;

    private BigDecimal totalEfectivoEnCaja; // (efectivo + monto inicial) - gastos
    private BigDecimal totalCtaBancaria;    // los depositos
    private BigDecimal totalCuadre;         // suma de los dos anteriores
    private String montoTexto;              // texto del monto anteior

}
