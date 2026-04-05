package com.svc.ventas.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_roles")
public class Rol implements Serializable {

	@Id
	private Integer idRol;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@Column(name = "DES_ROL")
	private String desRol;

}
