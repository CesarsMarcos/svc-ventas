package com.svc.ventas.models.mapstruct.dto;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotNull;

import com.svc.ventas.message.request.ProductoParaVender;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto {

	private String fecha;

	@NotNull
	private ClienteDto cliente;

	@NotNull
	private TipoDocumentoDto tipoDocumento;

	@NotNull
	private List<ProductoParaVender> productos;

	@NotNull
	private BigDecimal igv;

	@NotNull
	private BigDecimal subTotal;

	@NotNull
	private BigDecimal total;

}
