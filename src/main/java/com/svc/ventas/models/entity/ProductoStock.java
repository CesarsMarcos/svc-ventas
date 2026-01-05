package com.svc.ventas.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "tb_producto_stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_producto", "id_sucursal"})
        }
)
public class ProductoStock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_stock_producto"))
  private Producto producto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sucursal", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_stock_sucursal"))
  private Sucursal sucursal;

  private Integer stock;

  private Integer minCantidad;

  private Integer maxCantidad;

  //precio actual por sucursal (esto debe ser lo que se obtiene cuando se vende)
  private BigDecimal precioVenta;

  //precio de producto en promocion por sucursal
  private BigDecimal precioDescuento;

  public boolean sinStock() {
    return Objects.isNull(this.stock) || this.stock <= 0;
  }

  public void restarStock(Integer stock) {
    this.stock -= stock;
  }

  public void sumarStock(Integer stock) {
    this.stock += stock;
  }

}