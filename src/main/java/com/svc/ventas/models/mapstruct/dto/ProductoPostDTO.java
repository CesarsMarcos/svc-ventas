package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPostDTO {

	private Long idProducto;
	
	@NotNull
	private CategoriaGetDto categoria;

	@NotNull
	private MarcaGetDto marca;

	@NotNull
	private UnidadMedidaGetDto unidadMedida;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	private String imagen;

	@NotNull
	private BigDecimal precio;

	@NotNull
	private BigDecimal precioDescuento;

	@NotNull
	private BigDecimal precioProveedor;

	@NotNull
	private Integer maxCantidad;

	@NotNull
	private Integer minCantidad;

	@NotNull
	private Integer stock;

	
}
