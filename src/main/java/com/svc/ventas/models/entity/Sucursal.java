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
	private Integer idSucursal;

	private String direccion;

	private String email;

	@Column(name="ind_estado")
	private Boolean indEstado;

	private String logo;

	@Column(name="num_documento")
	private String numDocumento;

	@Column(name="razon_social")
	private String razonSocial;

	private String representante;

	private String telefono;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_sucursal_tipo_documento"))
	private TipoDocumento tipoDocumento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_REGISTRO", foreignKey = @ForeignKey(name = "FK_SUCURSAL_USUARIO_REG"))
	private Usuario usuRegistro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_MOD", foreignKey = @ForeignKey(name = "FK_SUCURSAL_USUARIO_MOD"))
	private Usuario usuActualizacion;

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