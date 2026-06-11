package com.svc.ventas.message.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class EmpresaPostRequest implements Serializable {

	@NotBlank(message = "Debes enviar el RUC")
	private String ruc;

	@NotBlank(message = "Debes enviar la Razón Social")
	private String razonSocial;

	@NotBlank(message = "Debes enviar el Nombre Comercial")
	private String nombreComercial;

	@NotBlank(message = "Debes enviar la dirección")
	private String direccion;

	@Email
	private String email;

	private String telefono;

	@NotBlank
	private String departamento;

	@NotBlank
	private String provincia;

	@NotBlank
	private String distrito;

	@NotBlank
	private String ubigeo;

	@NotBlank
	private String logo;

	@NotBlank
	private String nombreImpuesto;

	private double porcentajeImpuesto;

	@NotBlank
	private String simboloMoneda;

	@NotNull
	private Boolean aplicaImpuesto;

}