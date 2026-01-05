package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.persistence.*;

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

	@Column(name = "tipo_documento")
	@Enumerated(EnumType.STRING)
	private TipoDocumentoPersona tipoDocumento;

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