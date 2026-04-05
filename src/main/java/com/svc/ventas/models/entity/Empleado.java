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
@Table(name="tb_empleados")
public class Empleado implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empleado")
	private Integer idEmpleado;

	@Column(name="cod_empleado")
	private String codEmpleado;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_persona",foreignKey=@ForeignKey(name="fk_empleado_persona"))
	private Persona persona;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_sucursal",foreignKey=@ForeignKey(name="fk_empleado_sucursal"))
	private Sucursal sucursal;

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
		this.fecUpdate =  LocalDateTime.now();
	}

}