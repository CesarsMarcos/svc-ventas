package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPersonaDto {

	private Integer idTipoPersona;

	@NotBlank
	private String descripcion;

	private Boolean indEstado;
	

}
