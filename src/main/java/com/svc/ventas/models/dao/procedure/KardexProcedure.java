package com.svc.ventas.models.dao.procedure;

import com.svc.ventas.message.response.KardexResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KardexProcedure {

  private final JdbcTemplate jdbc;

  public List<KardexResponse> obtenerKardex(
          Long idSucursal,
          Long idProducto,
          LocalDate fechaInicio,
          LocalDate fechaFin
  ) {
    String sql = "CALL sp_kardex_movimientos(?,?,?,?)";

    return jdbc.query(sql, new Object[]{
            idSucursal,
            idProducto,
            fechaInicio,
            fechaFin
    }, kardexRowMapper);
  }

  private final RowMapper<KardexResponse> kardexRowMapper = (rs, rowNum) ->
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
                  rs.getInt("stock_acctual")
          );

}
