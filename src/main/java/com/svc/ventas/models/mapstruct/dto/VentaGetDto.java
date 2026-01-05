package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoDocumento;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaGetDto {

	private Long id;

	private String fecha;

	private String estado;

	private String serie;

	private Integer correlativo;

	@NotNull
	private ClienteGetVentaDto cliente;

	@NotNull
	private TipoDocumento tipoDocumento;

	@NotNull
	private List<ProductoDetalleDto> productos;

	@NotNull
	private float igv;

	@NotNull
	private float subTotal;

	@NotNull
	private float total;

}
