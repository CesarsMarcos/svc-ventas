package com.svc.ventas.service;

import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;

public interface IPrintDocumentoService {

  DetalleImpresionDto detailsImpresion(Long id);

}
