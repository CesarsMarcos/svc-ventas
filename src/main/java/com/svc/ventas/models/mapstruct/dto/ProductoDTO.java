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
public class ProductoDTO {

	private Long idProducto;

	@NotNull
	private CategoriaDto categoria;

	@NotNull
	private MarcaDto marca;

	@NotNull
	private UnidadMedidaDto unidadMedida;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	private String imagen;

	@NotNull
	private BigDecimal precioReferencial;

	private BigDecimal precioVenta;

	private Boolean estado;

}
