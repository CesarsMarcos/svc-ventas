package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoGetDto implements Serializable {

	private Integer idTipoDocumento;

	private String descripcion;

	private Integer tipo;

	private Boolean indEstado;


	/**
	 * 
	 */
	private static final long serialVersionUID = -5768797163426683049L;

	
}
