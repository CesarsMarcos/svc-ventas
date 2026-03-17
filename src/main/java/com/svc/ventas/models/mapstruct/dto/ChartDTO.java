package com.svc.ventas.models.mapstruct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ChartDTO implements Serializable {

  private Long numCompras;

  private Long numVentas;

  private Long numClientes;

  private Long numProveedores;

  private List<ProductoMasVendidoDTO> productosMasVendidos;

  private VariacionVentasDTO ventasHoy;

  private VariacionVentasDTO ventasSemana;

  private VariacionVentasDTO ventasMes;

  private List<VentasPorMesDTO> ventas12Meses;

  private List<BajoStockDTO> productosBajoStock;

  private List<UltimasVentasDTO> ultimasVentas;

}
