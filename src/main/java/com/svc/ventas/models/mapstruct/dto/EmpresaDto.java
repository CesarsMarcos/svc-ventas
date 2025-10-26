package com.svc.ventas.models.mapstruct.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@RequiredArgsConstructor
public class EmpresaDto implements Serializable {

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

	private Boolean indEstado;

}