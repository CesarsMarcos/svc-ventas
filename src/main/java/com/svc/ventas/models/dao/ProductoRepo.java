package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.svc.ventas.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepo extends CrudRepository<Producto, Long>,
        JpaSpecificationExecutor<Producto> {

  @Query("SELECT a FROM Producto a WHERE a.indEstado = true")
  List<Producto> listaActivos();

}
