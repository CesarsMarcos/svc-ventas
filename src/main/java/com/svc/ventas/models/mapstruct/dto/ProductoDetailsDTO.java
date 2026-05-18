package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.entity.ProductoPresentacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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

	private List<ProductoPresentacion> presentaciones;

	private Boolean estado;

}
