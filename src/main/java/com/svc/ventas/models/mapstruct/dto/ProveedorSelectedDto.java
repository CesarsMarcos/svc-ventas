package com.svc.ventas.models.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorSelectedDto {

	private Long idProveedor;

	private String ruc;

	private String razonSocial;


}
