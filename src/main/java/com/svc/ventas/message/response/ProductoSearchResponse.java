package com.svc.ventas.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductoSearchResponse {

	private Integer idProducto;
	
	private String descripcion;
	
	private String nombre;
	
	private float precio;
	
	private float stock;
	
	
}
