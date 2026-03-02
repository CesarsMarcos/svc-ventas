package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.svc.ventas.models.enums.TipoDocumentoPersona;
import jakarta.persistence.*;

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
	private Long idProveedor;

	@Column(name = "tipo_documento")
	@Enumerated(EnumType.STRING)
	private TipoDocumentoPersona tipoDocumento;

	private String numDocumento;

	private String razonSocial;

	private String correo;

	private String direccion;

	private String telefono;

	@Column(name = "representante")
	private String representante;

	@Column(name = "telefono_contacto")
	private String telefonoContacto;

	private String banco;

	private String nroCuenta;
	
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
