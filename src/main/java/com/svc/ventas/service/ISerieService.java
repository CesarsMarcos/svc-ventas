package com.svc.ventas.service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Serie;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.enums.TipoPago;
import com.svc.ventas.models.mapstruct.dto.SerieDTO;

import java.util.List;

public interface ISerieService {

  List<Serie> series();

  Response save(Serie serie);

  Serie get (Long id);

  SerieDTO getByIdDocumentType (Long idSucursal, TipoDocumento tipoDocumento);

}
