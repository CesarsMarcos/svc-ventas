package com.svc.ventas.models.dao;

import com.svc.ventas.models.mapstruct.dto.VentasPorMesDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.ventas.models.entity.Venta;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepo extends JpaRepository<Venta, Long>,
        JpaSpecificationExecutor<Venta>, PagingAndSortingRepository<Venta, Long> {

  @Query("SELECT v FROM Venta v WHERE v.cliente.persona.numDocumento =:documentoCliente")
  List<Venta> ventasPorDocumentoCliente(@Param("documentoCliente") String documentoCliente);

  @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecAdd BETWEEN :inicio AND :fin")
  BigDecimal obtenerSumaVentasPorRango(LocalDateTime inicio, LocalDateTime fin);

  @Query("SELECT new com.svc.ventas.models.mapstruct.dto.VentasPorMesDTO(" +
          "YEAR(v.fecAdd), MONTH(v.fecAdd), SUM(v.total)) " +
          "FROM Venta v " +
          "WHERE v.fecAdd >= :fechaInicio " +
          "GROUP BY YEAR(v.fecAdd), MONTH(v.fecAdd) " +
          "ORDER BY YEAR(v.fecAdd), MONTH(v.fecAdd)")
  List<VentasPorMesDTO> obtenerVentasUltimos12Meses(LocalDateTime fechaInicio);

  @Query("SELECT COUNT(v.idVenta) FROM Venta v WHERE v.fecAdd BETWEEN :inicio AND :fin")
  Long countVentas (LocalDateTime inicio, LocalDateTime fin);

}

