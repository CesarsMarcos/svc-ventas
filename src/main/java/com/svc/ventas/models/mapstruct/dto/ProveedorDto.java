package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDto {

	private Integer idProveedor;

	@NotNull
	private TipoDocumentoPersona tipoDocumento;

	@NotBlank
	private String numDocumento;

	@NotBlank
	private String razonSocial;

	@NotBlank
	private String correo;

	@NotBlank
	private String direccion;

	@NotBlank
	private String telefono;

	@NotBlank
	private String representante;

	@NotBlank
	private String telefonoContacto;

	private String banco;

	private String nroCuenta;

	private Boolean estado;

}
