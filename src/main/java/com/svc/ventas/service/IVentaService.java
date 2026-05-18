package com.svc.ventas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.svc.ventas.message.request.VentaRequest;
import com.svc.ventas.message.response.ResponseTransaccion;
import com.svc.ventas.models.entity.Venta;
import com.svc.ventas.models.enums.TipoDocumento;
import com.svc.ventas.models.mapstruct.dto.DetalleImpresionDto;
import com.svc.ventas.models.mapstruct.dto.EnumDto;
import com.svc.ventas.models.mapstruct.dto.ProductoSearchVentaDto;
import com.svc.ventas.models.mapstruct.dto.VentaDetailDto;
import org.springframework.transaction.annotation.Transactional;

public interface IVentaService {

  Map<String, Object> searchVenta(String nombre, String documentoCliente,
                                  String documentoVenta, LocalDate inicio,
                                  LocalDate fin, Integer page, Integer size);

  @Transactional
  ResponseTransaccion registrar(VentaRequest ventaDto);

  List<Venta> listadoVentasPorCliente(String dni);

  VentaDetailDto details(Long id);

  List<EnumDto> tipoPago();

  List<EnumDto> tipoDocumentoPersona();

  byte[] generarPdf(DetalleImpresionDto venta, TipoDocumento tipoDocumento);

  List<ProductoSearchVentaDto> buscarPorNombreOCodigoPresentacionesParaVenta(String termino);

	Map<String, Object> searchProductosVentaPos(String nombre, Long categoriaId,
                                                        int page, int size);


}
