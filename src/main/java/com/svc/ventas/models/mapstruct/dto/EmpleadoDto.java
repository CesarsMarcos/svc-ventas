package com.svc.ventas.models.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto {

	private Integer idEmpleado;

	private PersonaDto persona;

	private SucursalDto sucursal;

	private Boolean indEstado;

}
