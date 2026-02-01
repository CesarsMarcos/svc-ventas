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

	private String categoria;

	private String marca;

	private String nombre;

	private String imagen;
	
	private float precioVenta;
	
	private float stock;
	
	private Boolean estado;
}
