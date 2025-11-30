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
	
	private Long idProducto;

	private String nombre;

	private String descripcion;
	
	private Integer cantidad;

	private Integer cantidadRecibida;

	private BigDecimal precio;

}