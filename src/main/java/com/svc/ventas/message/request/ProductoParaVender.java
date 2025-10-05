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
public class ProductoParaVender extends ProductoPostDTO {

	private Integer cantidad;

	public ProductoParaVender(Long idProducto,
							  @NotNull CategoriaGetDto categoria, @NotNull MarcaGetDto marca,
							  @NotNull UnidadMedidaGetDto unidadMedida, @NotBlank String descripcion,
							  @NotBlank String nombre, @NotBlank String imagen, @NotNull BigDecimal precio,
							  @NotNull BigDecimal precioDescuento, @NotNull BigDecimal precioProveedor,
							  @NotNull Integer maxCantidad, @NotNull Integer minCantidad,
							  @NotNull Integer stock, @NotNull Integer cantidad) {
		super(idProducto, categoria, marca, unidadMedida, descripcion, nombre, imagen, precio, precioDescuento,
				precioProveedor, maxCantidad, minCantidad, stock);
		this.cantidad = cantidad;
	}

	public BigDecimal getTotal() {
		return this.getPrecio().multiply(new BigDecimal(this.cantidad));
	}

}
