package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_menus")
public class Menu implements Serializable{
	
	@Id
	private Integer idMenu;

	@Column(name = "icono", length = 20)
	private String icono;

	@Column(name = "nombre", length = 20)
	private String nombre;

	@Column(name = "url", length = 50)
	private String url;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_menu_rol", 
			joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"), 
			inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
	private List<Rol> roles;	

}
