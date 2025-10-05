package com.svc.ventas.util;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
@AllArgsConstructor
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idVenta;
	
	private LocalDate fechaYHora;

	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private Set<ProductoVendido> productos;

	public Venta() {
		this.fechaYHora = LocalDate.now();
	}

	public Float getTotal() {
		Float total = 0f;
		for (ProductoVendido productoVendido : this.productos) {
			total += productoVendido.getTotal();
		}
		return total;
	}

}