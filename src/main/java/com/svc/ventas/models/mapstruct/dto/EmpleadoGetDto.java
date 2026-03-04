package com.svc.ventas.models.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoGetDto {

	private String nombreCompleto;

	private String tipoDocumento;

	private String numDocumento;

	private String correo;

	private String fechaNacimiento;

	private String celular;

	private String foto;

	private String razonSocial;

	private String representante;

	private Boolean estado;

}
