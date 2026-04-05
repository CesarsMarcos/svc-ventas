package com.svc.ventas.service.impl;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.exception.EntityNotFoundException;
import com.svc.ventas.models.dao.VentaRepo;
import com.svc.ventas.models.entity.Empresa;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.mappers.ImpresionVentaMapper;
import com.svc.ventas.service.IPrintDocumentoService;
import com.svc.ventas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImpresionDocumentoVentaImpl implements IPrintDocumentoService {

  private final VentaRepo ventaRepo;

  private final AppContext appContext;

  private final ImpresionVentaMapper impresionVentaMapper;

  @Override
  public DetalleImpresionDto detailsImpresion(Long id) {
    Venta venta = ventaRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(Constantes.MENSAJE_NOT_FOUND, "Venta", id)));
    Empresa empresa = appContext.getEmpresa();

    return impresionVentaMapper.toDto(empresa, venta);
  }
}
