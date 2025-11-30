package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepo extends JpaRepository<Rol, Integer> {

}
