package com.svc.ventas.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_roles")
public class Rol implements Serializable {

	@Id
	private Integer idRol;

	@Column(name = "DES_ROL")
	private String desRol;

}
