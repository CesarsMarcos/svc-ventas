package com.svc.ventas.models.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.svc.ventas.models.entity.Cliente;

import java.util.List;

public interface ClienteRepo extends CrudRepository<Cliente, Integer>,
        JpaSpecificationExecutor<Cliente>, PagingAndSortingRepository<Cliente, Integer> {

	@Query("SELECT c FROM Cliente c")
  List<Cliente> clientesactivos();

  Boolean existsByPersonaIdPersona(Integer idPersona);

  @Query("SELECT COUNT(c) FROM Cliente c WHERE c.persona.indEstado = true")
  Long numClientesActivos();

}
