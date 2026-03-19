package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.svc.ventas.models.enums.EstadoCaja;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_cajas")
public class Caja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caja")
    private Long idCaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_USUARIO", foreignKey = @ForeignKey(name = "FK_CAJA_USUARIO"), nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_sucursal",foreignKey=@ForeignKey(name="fk_caja_sucursal"))
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @JsonManagedReference
    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL)
    private List<CajaMovimiento> movimientos;

    private String fecha;

    private String horaApertura;

    private BigDecimal montoApertura;

    private String horaCierre;

    private BigDecimal montoCierre;

    private BigDecimal montoCalculado;

    private BigDecimal diferencia;

    @Enumerated(EnumType.STRING)
    private EstadoCaja estado;

    @Column(name = "created_by")
    private String  createdBy;

    @Column(name = "updated_by")
    private String  updatedBy;

    @Column(name = "fec_add")
    private LocalDateTime fecAdd;

    @PrePersist
    protected void onCreate() {
        this.fecAdd = LocalDateTime.now();
    }

}
