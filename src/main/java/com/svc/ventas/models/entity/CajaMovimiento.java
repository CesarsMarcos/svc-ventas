package com.svc.ventas.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.svc.ventas.models.enums.TipoMovimiento;
import com.svc.ventas.models.enums.TipoPago;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_caja_movimientos")
public class CajaMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caja_movimiento")
    private Long idCajaMovimiento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caja")
    private Caja caja;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TipoMovimiento tipoMovimiento;

    private String documento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago")
    private TipoPago tipoPago;

    private BigDecimal monto;

    private String descripcion;

    @Column(name = "fec_add")
    private LocalDateTime fecAdd;

    @PrePersist
    protected void onCreate() {
        this.fecAdd = LocalDateTime.now();
    }

}
