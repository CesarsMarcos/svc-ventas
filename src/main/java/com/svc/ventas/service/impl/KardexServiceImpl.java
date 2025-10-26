package com.svc.ventas.service.impl;

import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;
import com.svc.ventas.models.mapstruct.dto.KardexResumenDTO;
import com.svc.ventas.service.IKardexService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KardexServiceImpl implements IKardexService {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<KardexDetalleDTO> obtenerKardexPorProducto(Long idProducto) {
    String sql = """
                SELECT 
                    fecha_movimiento, tipo_movimiento, documento,
                    cantidad_entrada, cantidad_salida,
                    costo_unitario, total_movimiento,
                    SUM(cantidad_entrada - cantidad_salida) 
                        OVER (ORDER BY fecha_movimiento) AS saldo
                FROM vw_kardex
                WHERE id_producto = ?
                ORDER BY fecha_movimiento
            """;

    return jdbcTemplate.query(sql, new Object[]{idProducto}, (rs, rowNum) ->
            KardexDetalleDTO.builder()
                    .fechaMovimiento(rs.getDate("fecha_movimiento").toLocalDate())
                    .tipoMovimiento(rs.getString("tipo_movimiento"))
                    .documento(rs.getString("documento"))
                    .cantidadEntrada(rs.getBigDecimal("cantidad_entrada"))
                    .cantidadSalida(rs.getBigDecimal("cantidad_salida"))
                    .saldo(rs.getBigDecimal("saldo"))
                    .costoUnitario(rs.getBigDecimal("costo_unitario"))
                    .totalMovimiento(rs.getBigDecimal("total_movimiento"))
                    .build()
    );
  }

  @Override
  public List<KardexResumenDTO> listarKardexPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {

    String sql = """
            SELECT
                id_producto,
                codigo_producto,
                producto,
                SUM(cantidad_entrada) AS entradas,
                SUM(cantidad_salida) AS salidas
            FROM vw_kardex
            WHERE fecha_movimiento BETWEEN ? AND ?
            GROUP BY id_producto, codigo_producto, producto
            ORDER BY producto
        """;

    return jdbcTemplate.query(sql,
            new Object[]{fechaInicio, fechaFin},
            (rs, rowNum) -> {
              BigDecimal entradas = rs.getBigDecimal("entradas");
              BigDecimal salidas = rs.getBigDecimal("salidas");

              // Opcional: calcular saldo inicial con otra consulta sobre la misma vista
              BigDecimal saldoInicial = jdbcTemplate.queryForObject(
                      "SELECT COALESCE(SUM(cantidad_entrada) - SUM(cantidad_salida), 0) " +
                              "FROM vw_kardex WHERE id_producto = ? AND fecha_movimiento < ?",
                      new Object[]{rs.getLong("id_producto"), fechaInicio},
                      BigDecimal.class
              );

              BigDecimal saldoFinal = saldoInicial.add(entradas).subtract(salidas);

              return KardexResumenDTO.builder()
                      .idProducto(rs.getLong("id_producto"))
                      .codigo(rs.getString("codigo_producto"))
                      .nombre(rs.getString("producto"))
                      .saldoInicial(saldoInicial)
                      .entradas(entradas)
                      .salidas(salidas)
                      .saldoFinal(saldoFinal)
                      .build();
            });
  }
}
