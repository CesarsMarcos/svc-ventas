package com.svc.ventas.models.mapstruct.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoVendidoDto {

	private Integer idProductoVendido;
	
	private Float cantidad, precio, total;

	public Float getTotal() {
		return this.cantidad * this.precio;
	}

}
