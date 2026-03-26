package com.svc.ventas.message.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ResponseTransaccion {

	private String mensaje;

	private BigDecimal total;

	private String serieCorrelativo;

	private String tipoDocumento;

	private String tipoPago;

}
