package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Persona;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonaRepository extends CrudRepository<Persona, Integer>, JpaSpecificationExecutor<Persona>, PagingAndSortingRepository<Persona, Integer> {

  Boolean existsBynumDocumento(String documento);

  @Query("""
          SELECT p
          FROM Persona p
          WHERE NOT EXISTS (
           SELECT 1 FROM Usuario u WHERE u.persona.id = p.id
          )
          """)
  List<Persona> getPersonasParaUsuario();

  @Query("""
          SELECT p
          FROM Persona p
          WHERE NOT EXISTS (
              SELECT 1 FROM Empleado e WHERE e.persona.id = p.id)
          """)
  List<Persona> findDisponiblesParaEmpleado();

  @Query("""
          SELECT p
          FROM Persona p
          WHERE NOT EXISTS (
              SELECT 1 FROM Cliente c WHERE c.persona.id = p.id)
          """)
  List<Persona> findPersonasQueNoSonClientes();

}
