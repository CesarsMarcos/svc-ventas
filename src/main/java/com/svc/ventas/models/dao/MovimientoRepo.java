package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.CajaMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepo extends JpaRepository<CajaMovimiento, Long> {
}
