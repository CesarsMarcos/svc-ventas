package com.svc.ventas.models.mapstruct.dto;

import java.util.List;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetDto {

	private Integer idUsuario;
	
	private EmpleadoGetDto empleado;

	private SucursalGetDto sucursal;

	private List<RolDto> roles;
	
	private String usuario;

}
