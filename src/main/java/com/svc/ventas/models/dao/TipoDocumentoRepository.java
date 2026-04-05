package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento,Long> {

  @Query("""
            SELECT new com.svc.ventas.models.mapstruct.dto.TipoDocumentoDTO (
            td.idTipoDocumento,
            td.codigoSunat,
            td.descripcion
            )
            FROM EmpresaTipoDocumento etd
            JOIN etd.tipoDocumento td
            WHERE etd.empresa.idEmpresa = :idEmpresa
             AND etd.indEstado = true
            AND td.indEstado = true
            AND td.generaSerie = true
          """)
  List<TipoDocumentoDTO> tipoDocumentos(Long idEmpresa);

}
