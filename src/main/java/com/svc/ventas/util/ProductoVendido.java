package com.svc.ventas.util;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Entity
@Setter
@Getter
@AllArgsConstructor
public class ProductoVendido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Float cantidad, precio;
	private String nombre, codigo;
	@ManyToOne
	@JoinColumn
	private Venta venta;

	public Float getTotal() {
		return this.cantidad * this.precio;
	}

}