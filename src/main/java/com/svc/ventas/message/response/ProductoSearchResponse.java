package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductoSearchResponse {

	private Integer idProducto;

	private String codigo;

	private String categoria;

	private String marca;

	private String nombre;

	private String imagen;
	
	private BigDecimal precioBase;

	private Boolean estado;
}
