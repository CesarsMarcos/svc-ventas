package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long idProducto;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "fk_articulo_categoria"))
	private Categoria categoria;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_marca", foreignKey = @ForeignKey(name = "fk_articulo_marca"))
	private Marca marca;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_umedida", foreignKey = @ForeignKey(name = "fk_articulo_unidad_medida"))
	private UnidadMedida unidadMedida;

	private String descripcion;

	private String nombre;

	private String codigo;

	private String imagen;

	private BigDecimal precio;

	private BigDecimal precioDescuento;

	private BigDecimal precioProveedor;

	private Integer maxCantidad;

	private Integer minCantidad;

	private Integer stock;

	@Transient
	private Integer cantidad;

	/*
	 * private double costoCompra;
	 * 
	 * private double costoVenta;
	 */

	@Column(name = "ind_estado")
	private Boolean indEstado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_REGISTRO", foreignKey = @ForeignKey(name = "FK_PRODUCTO_USUARIO_REG"))
	private Usuario usuRegistro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_MOD", foreignKey = @ForeignKey(name = "FK_PRODUCTO_USUARIO_MOD"))
	private Usuario usuActualizacion;

	@Column(name = "fec_add")
	private LocalDateTime fecAdd;

	@Column(name = "fec_update")
	private LocalDateTime fecUpdate;

	@PrePersist
	protected void onCreate() {
		this.fecAdd = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.fecUpdate = LocalDateTime.now();
	}

}