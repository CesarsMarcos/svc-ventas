package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_proveedores")
public class Proveedor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_proveedor")
	private Integer idProveedor;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_proveedor_tipo_documento"))
	private TipoDocumento tipoDocumento;

	private String numDocumento;

	private String razonSocial;

	private String correo;

	private String direccion;

	private String telefono;

	@Column(name = "representante")
	private String representante;

	@Column(name = "telefono_contacto")
	private String telefonoContacto;
	
	@Column(name = "ind_estado")
	private Boolean indEstado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_USUARIO_REGISTRO", foreignKey = @ForeignKey(name = "FK_PROVEEDOR_USUARIO_REG"))
	private Usuario usuRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_USUARIO_MOD", foreignKey = @ForeignKey(name = "FK_PROVEEDOR_USUARIO_MOD"))
	private Usuario usuMod;

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
