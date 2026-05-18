package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {

	@NotNull
	private TipoDocumentoPersona tipoDocumento;

	@NotBlank
	private String numDocumento;

	private String nombre;

	private String apeMaterno;

	private String apePaterno;

	private String razonSocial;
	
	@Email
	private String correo;

	private String direccion;

	private String telefono;

	private String celular;

	private String fechaNacimiento;

	private String foto;

	private Boolean indEstado;

}
