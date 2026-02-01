package com.svc.ventas.models.mapstruct.dto;

import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPago;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetailsDto {

	private Long id;

	private String fecha;

	private String estado;

	private String serie;

	private Integer correlativo;

	private TipoPago tipoPago;

	@NotNull
	private ClienteGetVentaDto cliente;

	@NotNull
	private TipoDocumento tipoDocumento;

	@NotNull
	private List<ProductoDetalleCompraDto> productos;

	@NotNull
	private float igv;

	@NotNull
	private float subTotal;

	@NotNull
	private float total;

}
