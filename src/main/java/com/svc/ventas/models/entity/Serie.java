package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_series", uniqueConstraints = @UniqueConstraint(columnNames = {
        "id_empresa_", "id_sucursal", "id_tipo_documento", "serie"
}))
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idSerie;

  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_documento", foreignKey = @ForeignKey(name = "fk_serie_tipo_documento"))
  private TipoDocumento tipoDocumento;

  @JsonIgnore
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sucursal", foreignKey = @ForeignKey(name = "fk_serie_sucursal"))
  private Sucursal sucursal;

  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_serie_empresa"))
  private Empresa empresa;

  @Pattern(regexp = "^[FB]\\d{3}$", message = "La serie debe tener formato F001 o B001")
  private String serie;

  private Long correlativo;

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
