package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_producto_stock_presentaciones")
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockPresentacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_presentacion")
  private Long idPresentacion;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto_stock")
  private ProductoStock productoStock;

  private String nombre;

  private BigDecimal equivalencia;

  //precio de producto en promocion por sucursal
  private BigDecimal precioDescuento;

  //precioSugerido = costo (que es precio de compra) * (1 * margen) es una referencia
  @Column(name = "precio_sugerido", precision = 14, scale = 2, nullable = false)
  private BigDecimal precioSugerido;

  //esto debe ser editable
  @Column(name = "precio_venta", precision = 14, scale = 2, nullable = false)
  private BigDecimal precioVenta;


  private Boolean isPrincipal;

  @Column(name = "ind_estado", nullable = false)
  private Boolean estado;

}
