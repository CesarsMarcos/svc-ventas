package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_sucursales")
public class Sucursal implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_sucursal")
	private Long idSucursal;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_sucursal_empresa"))
	private Empresa empresa;

	private String codigo;

	private String direccion;

	private String email;

	@Column(name="ind_estado")
	private Boolean indEstado;

	@Column(name="num_documento")
	private String numDocumento;

	@Column(name="razon_social")
	private String razonSocial;

	private String representante;

	private String telefono;

	@Column(name = "created_by")
	private String  createdBy;

	@Column(name = "updated_by")
	private String  updatedBy;

	@Column(name="fec_add")
	private LocalDateTime fecAdd;

	@Column(name="fec_update")
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