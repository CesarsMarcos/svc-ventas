package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolDto {
	private Integer idRol;

	@NotBlank
	private String desRol;
}
