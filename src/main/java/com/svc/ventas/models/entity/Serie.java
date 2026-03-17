package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.svc.ventas.models.enums.TipoDocumento;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_series")
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idSerie;

  @Column(name = "tipo_documento")
  @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;

  @JsonIgnore
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sucursal", foreignKey = @ForeignKey(name = "fk_serie_sucursal"))
  private Sucursal sucursal;

  @ManyToOne
  @JoinColumn(name = "id_empresa")
  private Empresa empresa;

  private String serie;

  private Integer correlativo;

  @Column(name="ind_estado")
  private Boolean indEstado;

  @Column(name = "created_by")
  private String  createdBy;

  @Column(name = "updated_by")
  private String  updatedBy;

  @Column(name = "fec_add")
  private LocalDateTime fecAdd;

  @Column(name = "fec_update")
  private LocalDateTime fecUpdate;

  @PrePersist
  protected void onCreate() {
    this.fecAdd = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.fecUpdate = LocalDateTime.now();
  }

}
