package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaGetDto {

	private Long id;

	private LocalDate fecha;

	private String tipoPago;

	private String documento;

	private String cliente;

	private String estado;

	@NotNull
	private BigDecimal igv;

	@NotNull
	private BigDecimal subTotal;

	@NotNull
	private BigDecimal total;

}
