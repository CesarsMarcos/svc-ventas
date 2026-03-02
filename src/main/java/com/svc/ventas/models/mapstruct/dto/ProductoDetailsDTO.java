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
public class ProductoDetailsDTO {

	private Long idProducto;

	private String codigo;
	
	@NotNull
	private String categoria;

	@NotNull
	private String marca;

	@NotNull
	private String unidadMedida;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	private String imagen;

	@NotNull
	private BigDecimal precioBase;

	private Boolean estado;

}
