package com.svc.ventas.models.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoSelectedDto implements Serializable {
	private Integer idTipoDocumento;

	private String descripcion;


	/**
	 * 
	 */
	private static final long serialVersionUID = -5768797163426683049L;

	
}
