package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.message.request.PersonaRequest;
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
	private PersonaRequest persona;
	
}
