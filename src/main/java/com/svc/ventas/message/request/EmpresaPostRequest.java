package com.svc.ventas.message.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class EmpresaPostRequest implements Serializable {

	@NotBlank(message = "Debes enviar el RUC")
	@Pattern(
					regexp = "^\\d{11}$",
					message = "El RUC debe contener 11 dígitos"
	)
	private String ruc;

	@NotBlank(message = "Debes enviar la Razón Social")
	@Size(min = 3, max = 200,
					message = "La razón social debe tener entre 3 y 200 caracteres")
	private String razonSocial;

	@NotBlank(message = "Debes enviar el Nombre Comercial")
	@Size(min = 3, max = 200,
					message = "El nombre comercial debe tener entre 3 y 200 caracteres")
	private String nombreComercial;

	@NotBlank(message = "Debes enviar la dirección")
	@Size(min = 5, max = 250,
					message = "La dirección debe tener entre 5 y 250 caracteres")
	private String direccion;

	@Email(message = "El correo electrónico no es válido")
	@Size(max = 50,
					message = "El correo no puede exceder 50 caracteres")
	private String email;

	private String telefono;

	@NotBlank(message = "El departamento es obligatorio")
	@Size(max = 100)
	private String departamento;

	@NotBlank(message = "La provincia es obligatoria")
	@Size(max = 100)
	private String provincia;

	@NotBlank(message = "El distrito es obligatorio")
	@Size(max = 100)
	private String distrito;

	@NotBlank(message = "El ubigeo es obligatorio")
	@Pattern(
					regexp = "^\\d{6}$",
					message = "El ubigeo debe contener 6 dígitos"
	)
	private String ubigeo;

	@NotBlank
	private String logo;

	@NotBlank(message = "El nombre del impuesto es obligatorio")
	@Size(max = 50)
	private String nombreImpuesto;

	@NotNull(message = "El porcentaje de impuesto es obligatorio")
	@DecimalMin(value = "0.00",
					message = "El porcentaje de impuesto no puede ser negativo")
	@DecimalMax(value = "100.00",
					message = "El porcentaje de impuesto no puede superar 100")
	private BigDecimal porcentajeImpuesto;

	@NotBlank(message = "El símbolo de moneda es obligatorio")
	@Size(max = 10)
	private String simboloMoneda;

	@NotNull(message = "Debe indicar si aplica impuesto")
	private Boolean aplicaImpuesto;

}