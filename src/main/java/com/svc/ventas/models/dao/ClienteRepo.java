package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.svc.ventas.models.entity.Cliente;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepo extends CrudRepository<Cliente, Integer>,
        JpaSpecificationExecutor<Cliente>, PagingAndSortingRepository<Cliente, Integer> {

	@Query("SELECT c FROM Cliente c WHERE c.indEstado = true AND c.persona.empresa = :empresa")
  List<Cliente> clientesActivosPorEmpresa(@Param("empresa") Empresa empresa);

  Boolean existsByPersonaIdPersona(Integer idPersona);

  @Query("SELECT COUNT(c) FROM Cliente c WHERE c.indEstado = true")
  Long numClientesActivos();

}
