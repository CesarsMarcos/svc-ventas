package com.svc.ventas.models.dao;

import java.util.List;
import java.util.Optional;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svc.ventas.models.entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

	@Query("""
					SELECT u FROM Usuario u
					JOIN FETCH u.sucursal
					WHERE u.usuario = :usuario
					AND u.indEstado = true
					""")
	Optional<Usuario> getByUserName(@Param ("usuario") String userName);

	@Query("SELECT u FROM Usuario u WHERE u.indEstado = true AND u.persona.empresa = :empresa")
	List<Usuario> getUsuariosActivos(@Param("empresa") Empresa empresa);

	@Query("""
					SELECT u
					FROM Usuario u
					WHERE
					    LOWER(u.persona.numDocumento) LIKE LOWER(CONCAT('%', :termino, '%'))
					OR  LOWER(CONCAT(u.persona.nombres, ' ', u.persona.apePaterno))
					    LIKE LOWER(CONCAT('%', :termino, '%'))
					OR  LOWER(CONCAT(u.persona.nombres, ' ', u.persona.apePaterno, ' ', u.persona.apeMaterno))
					    LIKE LOWER(CONCAT('%', :termino, '%'))
					""")
	Page<Usuario> findUsuario(@Param("termino") String termino, Pageable pageable);

	Boolean existsByPersonaIdPersona(Integer idPersona);

	Boolean existsByEmpleadoIdEmpleado(Integer idEmpleado);

}
