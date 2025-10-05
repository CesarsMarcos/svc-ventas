package com.svc.ventas.models.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorGetDto {

	private Integer idProveedor;

	private TipoDocumentoGetDto tipoDocumento;

	private String numDocumento;

	private String razonSocial;

	private String correo;

	private String direccion;

	private String telefono;

	private String representante;

	private String telefonoContacto;

}
