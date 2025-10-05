package com.svc.ventas.models.mapstruct.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoGetDTO {

	private Long idProducto;
	
	@NotNull
	private CategoriaGetDto categoria;

	@NotNull
	private MarcaGetDto marca;

	@NotNull
	private UnidadMedidaGetDto unidadMedida;

	@NotBlank
	private String descripcion;

	@NotBlank
	private String nombre;

	@NotBlank
	private String imagen;

	@NotNull
	private BigDecimal precio;

	@NotNull
	private BigDecimal precioDescuento;

	@NotNull
	private BigDecimal precioProveedor;

	@NotNull
	private Integer maxCantidad;

	@NotNull
	private Integer minCantidad;

	@NotNull
	private Integer stock;

	private Boolean indEstado;

	public boolean sinStock() {
		return this.stock <= 0;
	}

	public void restarStock(Integer stock) {
		this.stock -= stock;
	}

	public void sumarStock(Integer stock) {
		this.stock += stock;
	}
	
}
