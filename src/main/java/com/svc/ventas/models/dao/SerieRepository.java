package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

  Optional<Serie> findByTipoDocumentoIdTipoDocumento(Integer idTipoDocumento);

}
