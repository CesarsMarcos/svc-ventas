package com.svc.ventas.models.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteSelectedDto {

	private Long idCliente;

	private String nombreCompleto;

	private String tipoDocumento;

	private String numDocumento;

	private Boolean isClienteFinal;
	
}
