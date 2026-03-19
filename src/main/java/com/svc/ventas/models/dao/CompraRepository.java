package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface CompraRepository extends JpaRepository<Compra, Long>,
        JpaSpecificationExecutor<Compra> {

  Boolean existsBySerieAndCorrelativoAndSucursalIdSucursal(String serie, String correlativo, Long idSucursal);

  @Query("SELECT COUNT(c.idCompra) FROM Compra c WHERE c.fecAdd BETWEEN :inicio AND :fin")
  Long countCompras (LocalDateTime inicio, LocalDateTime fin);

}
