package com.svc.ventas.message.request;

import com.svc.ventas.models.enums.TipoDocumento;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequest {

	private String fecha;

	@NotNull
	private Integer idCliente;

	@NotNull
	private Long idSucursal;

	@NotNull
	private TipoDocumento tipoDocumento;

	private Boolean aplicarImpuesto;

	private String tipoPago;

	@NotNull
	private List<ProductoParaVender> productos;

	@NotNull
	private BigDecimal igv;

	@NotNull
	private BigDecimal subTotal;

	@NotNull
	private BigDecimal total;

}
