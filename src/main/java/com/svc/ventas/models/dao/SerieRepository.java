package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Serie;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
              SELECT s FROM Serie s
              WHERE s.empresa.id = :empresaId
              AND s.sucursal.id = :sucursalId
              AND s.tipoDocumento.id = :tipoDocumentoId
              AND s.indEstado = true
          """)
  Optional<Serie> obtenerSerieForUpdate(Long empresaId, Long sucursalId, Long tipoDocumentoId);

  @Query("""
              SELECT s
              FROM Serie s
              WHERE s.empresa.idEmpresa = :empresaId
                AND s.sucursal.idSucursal = :sucursalId
                AND s.tipoDocumento.idTipoDocumento = :tipoDocumentoId
                AND s.indEstado = true
          """)
  Optional<Serie> getSerie(Long empresaId, Long sucursalId, Long tipoDocumentoId);

  @Query("""
              SELECT COUNT(s) > 0
              FROM Serie s
              WHERE s.empresa.idEmpresa = :empresaId
                AND s.sucursal.idSucursal = :sucursalId
                AND s.tipoDocumento.idTipoDocumento = :tipoDocumentoId
                AND s.serie = :serie
                AND s.indEstado = true
          """)
  Boolean existsBySerie (Long empresaId, Long sucursalId, Long tipoDocumentoId, String serie);

}
