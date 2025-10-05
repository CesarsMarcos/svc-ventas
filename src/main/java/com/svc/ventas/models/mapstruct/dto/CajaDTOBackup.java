package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CajaDTOBackup {

    private Long idCash;

    private UsuarioGetDto usuario;

    private VentaGetDto venta;

    private String fecha;

    private String horaApertura;

    private BigDecimal montoApertura;

    private String horaCierre;

    private BigDecimal montoCierre;

    private BigDecimal montoCalculado;

    private BigDecimal diferencia;

    private String estado; //abierto cerrado

}
