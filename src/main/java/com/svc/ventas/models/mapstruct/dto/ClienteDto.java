package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

	private Integer idCliente;
	
	@NotNull
	private PersonaDto persona;
	
}
