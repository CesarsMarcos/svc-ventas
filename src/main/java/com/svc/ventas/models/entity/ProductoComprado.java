package com.svc.ventas.models.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_producto_comprado")
public class ProductoComprado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto_comprado")
	private Long idProductoComprado;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_compra")
	private Compra compra;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	
	private Long idProducto;

	private Long idPresentacion;

	private String presentacion;

	private String nombre;

	private String descripcion;

	@Column(name = "precio_compra")
	private BigDecimal precioCompra;

	@Column(precision = 14, scale = 3)
	private BigDecimal cantidad;

	@Column(name = "cantidad_recibida")
	private Integer cantidadRecibida;

	@Column(name = "sub_total")
	private BigDecimal subTotal;
}