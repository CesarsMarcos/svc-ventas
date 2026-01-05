package com.svc.ventas.models.mapstruct.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDetalleDto {

	private Long idProducto;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	@NotNull
	private BigDecimal precioBase;

	private Integer cantidad;

	private BigDecimal total;

}
