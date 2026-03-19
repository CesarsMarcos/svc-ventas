package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteGetDto {

	private Integer idCliente;
	
	private String tipoDocumento;

	private String nombreCompleto;

	private String numDocumento;

	private String telefono;

	private Boolean indEstado;
	
}
