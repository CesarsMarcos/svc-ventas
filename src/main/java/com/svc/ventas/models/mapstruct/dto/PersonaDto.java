package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto {

	@NotNull
	private Integer idPersona;

	@NotNull
	private TipoDocumentoDto tipoDocumento;

	@NotBlank
	private String numDocumento;

	@NotBlank
	private String nombre;

	@NotBlank
	private String apeMaterno;

	@NotBlank
	private String apePaterno;
	
	@Email
	private String correo;

	@NotBlank
	private String direccion;

	@NotBlank
	private String telefono;

	@NotBlank
	private String celular;

	@NotBlank
	private String fechaNacimiento;

	@NotBlank
	private String foto;

	private Boolean indEstado;

}
