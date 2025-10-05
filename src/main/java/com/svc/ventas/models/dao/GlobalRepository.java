package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalRepository  extends JpaRepository<Empresa, Integer> {

}
