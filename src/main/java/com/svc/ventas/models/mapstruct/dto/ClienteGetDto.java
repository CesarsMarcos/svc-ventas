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
public class ClienteGetDto {

	private Integer idCliente;
	
	@NotNull
	private PersonaGetDto persona;
	
}
