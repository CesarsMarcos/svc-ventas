package com.svc.ventas.models.dao;

import com.svc.ventas.models.entity.ProductoComprado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoCompradoRepository extends JpaRepository<ProductoComprado, Integer> {
}
