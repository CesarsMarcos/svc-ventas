package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.message.request.PersonaRequest;
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

	private PersonaRequest persona;

	private SucursalDto sucursal;

	private Boolean indEstado;

}
