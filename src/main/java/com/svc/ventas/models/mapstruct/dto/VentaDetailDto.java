package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetailDto {

	private Long id;

	private String fecha;

	private String tipoPago;

	private String tipoDocumento;

	private String serieCorrelativo;

	private String cliente;

	private String estado;

	private List<ProductoDetalleVentaDto> productos;

	@NotNull
	private float igv;

	@NotNull
	private float subTotal;

	@NotNull
	private float total;

}
