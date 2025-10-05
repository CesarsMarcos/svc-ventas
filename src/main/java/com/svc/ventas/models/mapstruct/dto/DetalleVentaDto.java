package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotNull;

import com.svc.ventas.models.entity.ProductoVendido;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDto {
	
	private Integer idDetalleVenta;
	
	@NotNull
	private ProductoVendido producto;

	@NotNull
	private Integer cantidad;

	public BigDecimal getImporte() {
		return producto.getPrecio().multiply(new BigDecimal(this.cantidad));
	}
	
}
