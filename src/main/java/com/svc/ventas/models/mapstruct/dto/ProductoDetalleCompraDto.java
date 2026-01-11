package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDetalleCompraDto {

	private Long idProducto;

	private String descripcion;

	private String nombre;

	private Integer cantidad;

	private Integer cantidadRecibida;

	private BigDecimal precioCompra;

	private BigDecimal subTotal;

}
