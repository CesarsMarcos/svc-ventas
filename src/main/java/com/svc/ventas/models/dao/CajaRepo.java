package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CajaRepo extends JpaRepository<Caja, Long> {

    Optional<Caja> findByFechaAndUsuarioUsuario(String fecha, String usuario);

}
