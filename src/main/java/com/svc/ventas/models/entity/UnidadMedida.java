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
@Table(name="tb_unidad_medidas")
public class UnidadMedida implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_umedida")
	private Integer idUmedida;

	private String nombre;

	private String prefijo;

	private Boolean indEstado;

}