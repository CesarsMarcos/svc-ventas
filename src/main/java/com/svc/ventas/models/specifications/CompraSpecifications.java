package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.*;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import javax.swing.*;
import java.time.LocalDate;

public class CompraSpecifications {

  public static Specification<Compra> hasRUC(String ruc) {
    return (root, query, cb) -> {
      if (ruc == null || ruc.isBlank()) {
        return cb.conjunction();
      }
      Join<Compra, Proveedor> proveedorJoin = root.join("proveedor", JoinType.INNER);
      return cb.equal(proveedorJoin.get("numDocumento"), ruc);
    };
  }

  public static Specification<Compra> hasProveedor(String razonSocial) {
    return (root, query, cb) -> {
      if (razonSocial == null || razonSocial.isBlank()) {
        return cb.conjunction();
      }
      Join<Compra, Proveedor> proveedorJoin = root.join("proveedor", JoinType.INNER);
      Expression<String> texto = proveedorJoin.get("razonSocial");
      return cb.like(
              cb.upper(texto),
              "%" + razonSocial.toUpperCase() + "%");
    };
  }

  public static Specification<Compra> hasDocumento(String documento) {
    return (root, query, cb) -> {
      if (documento == null) {
        return cb.conjunction();
      }
      Expression<String> serie  = root.get("serie");
      Expression<String> correlativo = root.get("correlativo");
      Expression<String> serieCorrelativo = cb.concat(serie,  cb.concat("-",  correlativo));
      return cb.like(cb.lower(serieCorrelativo), "%" + documento.toLowerCase() + "%"
      );
    };
  }

  public static Specification<Compra> hasFechaBetween(LocalDate inicio, LocalDate fin) {
    return (root, query, cb) -> {
      if (inicio == null || fin == null) {
        return cb.conjunction();
      }
      return cb.between(root.get("fecha"), inicio, fin);
    };
  }

}
