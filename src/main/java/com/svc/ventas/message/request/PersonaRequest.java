package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {

	@NotNull(message = "El tipo de documento es obligatorio")
	private TipoDocumentoPersona tipoDocumento;

	@NotBlank
	private String numDocumento;

	@Size(max = 100,
					message = "El nombre no puede exceder 100 caracteres")
	private String nombre;

	@Size(max = 100,
					message = "El apellido materno no puede exceder 100 caracteres")
	private String apeMaterno;

	@Size(max = 100,
					message = "El apellido paterno no puede exceder 100 caracteres")
	private String apePaterno;

	@Size(max = 200,
					message = "La razón social no puede exceder 200 caracteres")
	private String razonSocial;

	@Email(message = "El correo electrónico no es válido")
	@Size(max = 150,
					message = "El correo electrónico no puede exceder 150 caracteres")
	private String correo;

	@Size(max = 250,
					message = "La dirección no puede exceder 250 caracteres")
	private String direccion;

	@Pattern(
					regexp = "^[0-9+\\-\\s]{7,20}$",
					message = "El teléfono tiene un formato inválido"
	)
	private String telefono;

	@Pattern(
					regexp = "^[0-9+\\-\\s]{7,20}$",
					message = "El celular tiene un formato inválido"
	)
	private String celular;

	private String fechaNacimiento;

	private String foto;

}
