package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockDTO {

	private Long idProducto;

	@NotNull
	private CategoriaDto categoria;

	@NotNull
	private MarcaDto marca;

	@NotNull
	private UnidadMedidaDto unidadMedida;

	@NotBlank
	private String nombre;

	private String imagen;

	private Integer maxCantidad;

	private Integer minCantidad;

	private Integer stock;

	private BigDecimal precioDescuento;

	private BigDecimal precioVenta;

	private Boolean estado;

}
