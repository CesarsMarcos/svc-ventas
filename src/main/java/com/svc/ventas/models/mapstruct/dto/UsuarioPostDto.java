package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostDto {

	@NotNull
	private EmpleadoGetDto empleado;

	@NotNull
	private SucursalGetDto sucursal;

	@NotNull
	private List<RolDto> roles;
	
	@NotBlank
	private String usuario;

	@NotBlank
	private String clave;

}
