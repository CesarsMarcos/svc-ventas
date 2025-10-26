package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_personas")
public class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	private Integer idPersona;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_persona_tipo_documento"))
	private TipoDocumento tipoDocumento;

	private String numDocumento;

	private String nombre;

	@Column(name = "ape_materno")
	private String apeMaterno;

	@Column(name = "ape_paterno")
	private String apePaterno;

	@Column(name = "ubigeo_inei", length = 10)
	private String ubigeoInei;

	private String correo;

	private String direccion;

	private String telefono;

	private String celular;

	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento;

	private String foto;

	@Column(name = "ind_estado")
	private Boolean indEstado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_REGISTRO", foreignKey = @ForeignKey(name = "FK_PERSONA_USUARIO_REG"))
	private Usuario usuRegistro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COD_USUARIO_MOD", foreignKey = @ForeignKey(name = "FK_PERSONA_USUARIO_MOD"))
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