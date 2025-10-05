package com.svc.ventas.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_tipo_personas")
public class TipoPersona implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_persona")
	private Integer idTipoPersona;

	private String descripcion;

	@Column(name="ind_estado")
	private Boolean indEstado;

}