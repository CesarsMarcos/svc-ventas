package com.svc.ventas.models.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_tipo_documentos")
public class TipoDocumento implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_documento")
	private Integer idTipoDocumento;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "tipo")
	private Integer tipo;

	@Column(name = "ind_estado")
	private Boolean indEstado;

}
