package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

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

	@ManyToOne
	@JoinColumn(name = "id_empresa", foreignKey=@ForeignKey(name="fk_menu_empresa"))
	private Empresa empresa;

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
