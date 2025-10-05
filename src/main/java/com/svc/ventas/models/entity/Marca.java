package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_marcas")
public class Marca implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_marca")
	private Integer idMarca;

	private String descripcion;

	@Column(name="ind_estado")
	private Boolean indEstado;

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
		this.fecUpdate =LocalDateTime.now(); 
	}

}