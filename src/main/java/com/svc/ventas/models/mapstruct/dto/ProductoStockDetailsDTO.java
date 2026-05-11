package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockDetailsDTO {

	private Long idProducto;

	private String codigo;

	@NotBlank
	private String nombre;

	private String sucursal;

	@NotNull
	private String categoria;

	private Integer maxCantidad;

	private Integer minCantidad;

	private BigDecimal costoPromedio;

	private Integer stock;
	
	private List<ProductoStockPresentacionDto> presentaciones;

}
