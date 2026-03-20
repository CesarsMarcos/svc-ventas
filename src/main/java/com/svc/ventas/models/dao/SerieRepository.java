package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Serie;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

  //@Lock(LockModeType.PESSIMISTIC_WRITE)
  //Optional<Serie> findForUpdateBySucursalIdSucursalAndTipoDocumento(Long idSucursal, TipoDocumento tipoDocumento);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
              SELECT s FROM Serie s
              WHERE s.empresa.id = :empresaId
              AND s.sucursal.id = :sucursalId
              AND s.tipoDocumento.id = :tipoDocumentoId
              AND s.indEstado = true
          """)
  Optional<Serie> obtenerSerieForUpdate(Long empresaId, Long sucursalId, Long tipoDocumentoId);

  boolean existsByEmpresaIdEmpresaAndSucursalIdSucursalAndTipoDocumentoIdTipoDocumentoAndSerie(Long empresaId, Long sucursalId, Long tipoDocumentoId, String serie);

}
