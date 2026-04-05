package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@JoinColumn(name = "id_empresa", foreignKey= @ForeignKey(name="fk_menu_empresa"))
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_menu_padre")
	@JsonBackReference
	private Menu idMenuPadre;

	@OneToMany(mappedBy = "idMenuPadre", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Menu> subMenus;

	@Column(name ="is_empleado")
	private Boolean isEmpleado = false;

}
