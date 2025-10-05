package com.svc.ventas.models.mapstruct.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoPostDto {

	@NotNull
	private PersonaGetDto persona;

	private Boolean indEstado;

}
