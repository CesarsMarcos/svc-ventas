package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
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
  private Long idProductoStock;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_stock_producto"))
  private Producto producto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sucursal", nullable = false, foreignKey = @ForeignKey(name = "fk_producto_stock_sucursal"))
  private Sucursal sucursal;

  @JsonManagedReference
  @OneToMany(mappedBy = "productoStock", cascade = CascadeType.ALL)
  private List<ProductoStockPresentacion> presentaciones;

  @Column(precision = 14, scale = 3)
  private BigDecimal stock = BigDecimal.ZERO;

  private Integer minCantidad;

  private Integer maxCantidad;
  
  //esto es de la compra se actualiza en cada compra
  private BigDecimal costoPromedio = BigDecimal.ZERO;

  @Column(name = "ind_estado")
  private Boolean estado = Boolean.TRUE;

  public boolean sinStock() {
    return Objects.isNull(this.stock) || this.stock.compareTo(BigDecimal.ZERO) <= 0;
  }

  public void restarStock(BigDecimal cantidad) {
    if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cantidad inválida");
    }

    if (this.stock == null) {
      this.stock = BigDecimal.ZERO;
    }

    if (this.stock.compareTo(cantidad) < 0) {
      throw new IllegalStateException("Stock insuficiente");
    }

    this.stock = this.stock.subtract(cantidad);

  }

  public void sumarStock(BigDecimal cantidad) {
    if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Cantidad inválida");
    }

    if (this.stock == null) {
      this.stock = BigDecimal.ZERO;
    }

    this.stock = this.stock.add(cantidad);
  }

}