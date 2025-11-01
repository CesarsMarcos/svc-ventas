package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.svc.ventas.models.enums.TipoPagoCompra;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_compras")
public class Compra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compra")
	private Long idCompra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proveedor", foreignKey = @ForeignKey(name = "fk_compra_proveedor"))
	private Proveedor proveedor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_compra_tipo_documento"))
	private TipoDocumento tipoDocumento;

	@JsonManagedReference
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	private Set<ProductoComprado> productos;

	@Enumerated(EnumType.STRING)
	private TipoPagoCompra tipoPago;

	private String serie;

	private String correlativo;

	private LocalDate fecha;

	@Column(name = "igv")
	private BigDecimal igv;

	@Column(name = "sub_total")
	private BigDecimal subTotal;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "estado", nullable = false)
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_REGISTRO", foreignKey = @ForeignKey(name = "FK_COMPRA_USUARIO_REG"))
	private Usuario usuRegistro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_MOD", foreignKey = @ForeignKey(name = "FK_COMPRA_USUARIO_MOD"))
	private Usuario usuMod;

	@Column(name = "user_update")
	private String userUpdate;

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
