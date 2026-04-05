package com.svc.ventas.service.documentoStrategy.documento;

import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.VentaDetailDto;

import java.io.IOException;

public interface DocumentoPdfStrategy {

  byte[] generar(DetalleImpresionDto venta);

}
