package com.svc.ventas.models.mapstruct.dto;


import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoDto {

	private Integer idTipoDocumento;

	@NotBlank
	private String descripcion;

	private Boolean indEstado;

}
