package com.svc.ventas.service;

import com.svc.ventas.message.request.SerieRequest;
import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;
import com.svc.ventas.models.mapstruct.dto.TipoDocumentoDTO;

import java.util.List;

public interface ISerieService {

  List<SerieDTO> series();

  Response save(SerieRequest serieRequest);

  Serie get (Long id);

  List<TipoDocumentoDTO> getTipoDocumento();

  String generarNumero(Long empresaId, Long sucursalId, Long tipoDocumentoId);

}
