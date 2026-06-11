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
public class EmpresaGetDto {

  private Integer idEmpresa;

  private String ruc;

  private String razonSocial;

  private String nombreComercial;

  private String direccion;

  private String email;

  private String telefono;

  private String departamento;

  private String provincia;

  private String distrito;

  private String ubigeo;

  private String logo;

  private String nombreImpuesto;

  private double porcentajeImpuesto;

  private String simboloMoneda;

  private Boolean aplicaImpuesto;

  private Integer numeroSucursales;

  private Boolean isUsaSucursales;

  private Boolean isUsaEmpleados;

  private BigDecimal margenDefault;

  private Boolean estado;

}
