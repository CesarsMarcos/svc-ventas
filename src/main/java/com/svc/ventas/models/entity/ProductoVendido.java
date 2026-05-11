package com.svc.ventas.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_producto_vendido")
public class ProductoVendido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto_vendido")
	private Long idProductoVendido;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venta")
	private Venta venta;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	
	private Long idProducto;

	private String nombre;

	private String descripcion;
	
	private BigDecimal cantidad;

	private BigDecimal precioDescuento;

	private BigDecimal precio;

	@Column(name = "sub_total")
	private BigDecimal subTotal;

}