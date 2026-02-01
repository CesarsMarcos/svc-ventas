package com.svc.ventas.message.request;

import com.svc.ventas.models.mapstruct.dto.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoParaVender extends ProductoDTO {

	private Integer cantidad;

	private BigDecimal precioVenta;

	public ProductoParaVender(Long idProducto,
														@NotNull CategoriaDto categoria, @NotNull MarcaDto marca,
														@NotNull UnidadMedidaDto unidadMedida, @NotBlank String descripcion,
														@NotBlank String nombre, String imagen, @NotNull BigDecimal precioReferencial,
														@NotNull BigDecimal precioVenta, Boolean indEstado) {
		super(idProducto, categoria, marca, unidadMedida, descripcion, nombre, imagen, precioReferencial,precioVenta, indEstado);
  }

	public BigDecimal getTotal() {
		return this.getPrecioReferencial().multiply(new BigDecimal(this.cantidad));
	}

}
