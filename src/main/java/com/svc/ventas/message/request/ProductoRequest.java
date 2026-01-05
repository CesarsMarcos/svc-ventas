package com.svc.ventas.message.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {

	private Long idProducto;

	@NotNull
	private Integer idCategoria;

	@NotNull
	private Integer idMarca;

	@NotNull
	private Integer idUnidadMedida;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	private String imagen;

	@NotNull
	@Min(0)
	private BigDecimal precioBase;

}
