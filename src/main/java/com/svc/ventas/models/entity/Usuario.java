package com.svc.ventas.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails, Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_empleado",foreignKey=@ForeignKey(name="fk_usuario_empleado"))
	private Empleado empleado;

	@JoinTable(name = "tb_usuario_rol", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Rol> roles;
	
	private String usuario;
	
	private String clave;
	
	@Column(name = "ind_estado")
	private Boolean indEstado;

	@Column(name = "fec_add")
	private LocalDateTime fecAdd;

	@Column(name = "fec_update")
	private LocalDateTime fecUpdate;

	@PrePersist
	protected void onCreate() {
		this.fecAdd = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.fecUpdate = LocalDateTime.now();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getDesRol().toUpperCase()))
				.toList();
	}

	@Override
	public String getPassword() {
		return clave;
	}

	@Override
	public String getUsername() {
		return usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}