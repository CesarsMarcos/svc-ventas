package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.enums.TipoDocumento;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Serie> findForUpdateBySucursalIdSucursalAndTipoDocumento(Long idSucursal, TipoDocumento tipoDocumento);

  Optional<Serie> findBySucursalIdSucursalAndTipoDocumento(Long idSucursal, TipoDocumento tipoDocumento);
}
