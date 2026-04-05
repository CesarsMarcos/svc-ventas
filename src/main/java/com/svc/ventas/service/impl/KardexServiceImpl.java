package com.svc.ventas.service.impl;

import com.svc.ventas.message.response.KardexResponse;
import com.svc.ventas.models.mapstruct.dto.KardexDetalleDTO;
import com.svc.ventas.service.IKardexService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KardexServiceImpl implements IKardexService {

  private final JdbcTemplate jdbcTemplate;

  private final EntityManager entityManager;

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
  public List<KardexResponse> listarKardexPorFecha(Long idSucursal, Long idProducto,
                                                   String fechaInicio, String fechaFin) {

    String sql = "CALL sp_kardex_movimientos(?,?,?,?)";

      return jdbcTemplate.query(sql, new Object[]{
              idSucursal,
              idProducto,
              Objects.nonNull(fechaInicio) && !fechaInicio.isBlank() ? Date.valueOf(fechaInicio) : null,
              Objects.nonNull(fechaFin) && !fechaFin.isBlank() ? Date.valueOf(fechaFin) : null
      }, kardexRowMapper);

  }

  private final RowMapper<KardexResponse> kardexRowMapper = (rs, rownum) ->
          new KardexResponse(
                  rs.getLong("id_producto"),
                  rs.getString("nombre"),
                  rs.getString("codigo_sucursal"),
                  rs.getString("usuario"),
                  rs.getDate("fecha_movimiento").toLocalDate(),
                  rs.getString("tipo_movimiento"),
                  rs.getString("documento"),
                  rs.getString("tipo_pago"),
                  rs.getInt("stock_anterior"),
                  rs.getInt("ingreso"),
                  rs.getInt("egreso"),
                  rs.getInt("stock_actual")
          );
}
