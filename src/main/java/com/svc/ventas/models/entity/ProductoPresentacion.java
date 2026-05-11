package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_producto_presentaciones")
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPresentacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_presentacion")
  private Long idPresentacion;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto")
  private Producto producto;

  private String nombre;

  private BigDecimal equivalencia;

  private Boolean isPrincipal;

  @Column(name = "ind_estado")
  private Boolean estado;

}
