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
public class ProductoStockSearchResponse {

	private Integer idProducto;

	private String codigo;

	private String categoria;

	private String marca;

	private String sucursal;

	private String nombre;

	private float stock;

	private Integer maxCantidad;

	private Integer minCantidad;

	private BigDecimal precioDescuento;

	private BigDecimal precioVenta;

}
