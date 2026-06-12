package com.svc.ventas.message.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {

	private Long idProducto;

	@NotBlank(message = "El código es obligatorio")
	@Size(min = 2, max = 30,
					message = "El código debe tener entre 2 y 30 caracteres")
	private String codigo;

	@NotNull(message = "La categoría es obligatoria")
	@Min(value = 1, message = "La categoría es inválida")
	private Integer idCategoria;

	@NotNull(message = "La marca es obligatoria")
	@Min(value = 1, message = "La marca es inválida")
	private Integer idMarca;

	@NotNull(message = "La unidad de medida es obligatoria")
	@Min(value = 1, message = "La unidad de medida es inválida")
	private Integer idUnidadMedida;

	@NotBlank(message = "La descripción es obligatoria")
	@Size(min = 3, max = 500,
					message = "La descripción debe tener entre 3 y 500 caracteres")
	private String descripcion;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(min = 2, max = 150,
					message = "El nombre debe tener entre 2 y 150 caracteres")
	private String nombre;

	private String imagen;

	@NotNull(message = "Debe indicar si maneja presentaciones")
	private Boolean manejaPresentaciones;

}
