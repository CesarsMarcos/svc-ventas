package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.ProductoPresentacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoPresentacionRepo extends JpaRepository<ProductoPresentacion, Long> {
}
