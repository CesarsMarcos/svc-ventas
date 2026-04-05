package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

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

	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "fk_articulo_categoria"))
	private Categoria categoria;

	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_marca", foreignKey = @ForeignKey(name = "fk_articulo_marca"))
	private Marca marca;

	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_umedida", foreignKey = @ForeignKey(name = "fk_articulo_unidad_medida"))
	private UnidadMedida unidadMedida;

	@ManyToOne
	@JoinColumn(name = "id_empresa", foreignKey=@ForeignKey(name="fk_caja_empresa"))
	private Empresa empresa;

	private String descripcion;

	private String nombre;

	private String codigo;

	private String imagen;

	//precio de referencia
	private BigDecimal precioBase;

	@Transient
	private Integer cantidad;

	@Column(name = "ind_estado")
	private Boolean indEstado;

	@Column(name = "created_by")
	private String  createdBy;

	@Column(name = "updated_by")
	private String  updatedBy;

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