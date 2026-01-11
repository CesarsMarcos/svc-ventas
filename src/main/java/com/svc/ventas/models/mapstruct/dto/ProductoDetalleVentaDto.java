package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDetalleVentaDto {

	private Long idProducto;

	private String descripcion;

	private String nombre;

	private Integer cantidad;

	private BigDecimal precioVenta;

	private BigDecimal subTotal;
	
}
