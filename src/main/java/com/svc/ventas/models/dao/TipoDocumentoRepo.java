package com.svc.ventas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svc.ventas.models.entity.TipoDocumento;

public interface TipoDocumentoRepo extends JpaRepository<TipoDocumento, Integer> {

    @Query("SELECT td FROM TipoDocumento td WHERE td.indEstado = true")
    List<TipoDocumento> tipoDocumentos();

}
