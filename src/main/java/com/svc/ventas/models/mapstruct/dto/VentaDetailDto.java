package com.svc.ventas.models.mapstruct.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetailDto {

	private Long id;

	private LocalDate fecha;

	private String tipoPago;

	private String tipoDocumento;

	private String serieCorrelativo;

	private String cliente;

	private String estado;

	private List<ProductoDetalleVentaDto> productos;

	@NotNull
	private BigDecimal igv;

	@NotNull
	private BigDecimal subTotal;

	@NotNull
	private BigDecimal total;

}
