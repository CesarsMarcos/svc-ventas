package com.svc.ventas.models.mapstruct.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

	private Integer idUsuario;
	
	private EmpleadoDto empleado;

	private SucursalDto sucursal;

	private List<RolDto> roles;
	
	private String usuario;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String clave;

}
