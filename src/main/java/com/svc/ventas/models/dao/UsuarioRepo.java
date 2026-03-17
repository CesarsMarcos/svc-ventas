package com.svc.ventas.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svc.ventas.models.entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE  u.usuario = :usuario ")
	Usuario getByUserName (@Param ("usuario") String login);

	@Query("SELECT u FROM Usuario u WHERE u.indEstado = true")
	List<Usuario> getUsuariosActivos();

	Boolean existsByEmpleadoIdEmpleado(Integer idEmpleado);

  Optional<Usuario> findByUsuario(String username);

}
