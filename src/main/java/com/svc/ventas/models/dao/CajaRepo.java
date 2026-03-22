package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CajaRepo extends JpaRepository<Caja, Long> {

    @Query("""
            SELECT c
            FROM Caja c
            WHERE c.fechaHoraApertura BETWEEN :inicio AND :fin
            AND c.usuario.usuario = :usuario
            AND c.empresa.idEmpresa = :idEmpresa
            """)
    Optional<Caja> findByFecha(LocalDateTime inicio,
                               LocalDateTime fin,
                               String usuario, Long idEmpresa);

}
