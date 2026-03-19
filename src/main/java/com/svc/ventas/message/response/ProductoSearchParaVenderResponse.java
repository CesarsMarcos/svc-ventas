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
public class ProductoSearchParaVenderResponse {

	private Integer idProducto;

	private String codigo;

	private String nombre;

	private float stock;

	private BigDecimal precioVenta;

	private String imagen;

	private Boolean estado;

}
