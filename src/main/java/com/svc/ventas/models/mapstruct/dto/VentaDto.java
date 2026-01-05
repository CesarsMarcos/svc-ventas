package com.svc.ventas.models.mapstruct.dto;

import java.math.BigDecimal;
import java.util.List;

import com.svc.ventas.models.enums.TipoPago;
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
	private String tipoDocumento;

	@NotNull
	private List<ProductoParaVender> productos;

	private Boolean aplicarImpuesto;

	private TipoPago tipoPago;

	@NotNull
	private BigDecimal igv;

	@NotNull
	private BigDecimal subTotal;

	@NotNull
	private BigDecimal total;

}
