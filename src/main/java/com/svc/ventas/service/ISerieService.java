package com.svc.ventas.service;

import com.svc.ventas.message.response.Response;
import com.svc.ventas.models.entity.Serie;

import java.util.List;

public interface ISerieService {

  List<Serie> series();

  Response save(Serie serie);

  Serie get (Long id);

  Serie getByIdDocumentType (Integer idTipoDocumento);

}
