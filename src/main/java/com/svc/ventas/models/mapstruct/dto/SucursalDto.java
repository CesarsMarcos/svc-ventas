package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SucursalDto {

	private Long idSucursal;
	
	@NotBlank
	private String direccion;

	@NotBlank
	@Email
	private String email;

	private String logo;

	@NotBlank
	private String numDocumento;

	@NotBlank
	private String razonSocial;

	@NotBlank
	private String representante;

	@NotBlank
	private String telefono;

	@NotBlank
	private TipoDocumentoDto tipoDocumento;

	private Boolean indEstado;

}
