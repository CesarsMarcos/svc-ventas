package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.svc.ventas.models.enums.EstadoVenta;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPago;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_ventas",
				uniqueConstraints = {
								@UniqueConstraint(
												name = "uk_venta_documento",
												columnNames = {
																"id_sucursal",
																"tipo_documento",
																"serie",
																"correlativo"
												}
								)
				}
)
public class Venta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_venta")
	private Long idVenta;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", foreignKey = @ForeignKey(name = "fk_venta_cliente"))
	private Cliente cliente;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sucursal", foreignKey = @ForeignKey(name = "fk_venta_sucursal"))
	private Sucursal sucursal;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@Column(name = "tipo_documento")
	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDocumento;

	private String serie;

	private Long correlativo;

	@JsonManagedReference
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private Set<ProductoVendido> productos;

	@Enumerated(EnumType.STRING)
	private TipoPago tipoPago;

	private Boolean aplicarImpuesto;

	@Column(name = "igv")
	private BigDecimal igv;

	private LocalDate fecha;

	@Column(name = "sub_total")
	private BigDecimal subTotal;

	@Column(name = "total")
	private BigDecimal total;

	@Enumerated(EnumType.STRING)
	private EstadoVenta estado;

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
