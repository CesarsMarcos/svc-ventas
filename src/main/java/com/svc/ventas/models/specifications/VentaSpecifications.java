package com.svc.ventas.models.specifications;

import com.svc.ventas.config.AppContext;
import com.svc.ventas.models.entity.*;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class VentaSpecifications {

  public static Specification<Venta> filtroSeguridad(AppContext appContext) {

    return (root, query, cb) -> {

      if (appContext.isAdmin()) {
        return cb.conjunction();
      }

      if (appContext.isSupervisor()) {
        return cb.equal(
                root.get("sucursal"),
                appContext.getSucursal()
        );
      }

      return cb.equal(
              root.get("createdBy"),
              appContext.getUserName()
      );
    };

  }

  public static Specification<Venta> hasClienteNombre(String nombre) {
    return (root, query, cb) -> {
      if (nombre == null || nombre.isBlank()) {
        return cb.conjunction();
      }
      Join<Venta, Cliente> clienteJoin = root.join("cliente", JoinType.INNER);
      Join<Cliente, Persona> personaJoin = clienteJoin.join("persona", JoinType.INNER);

      Expression<String> nombreCompleto = cb.concat(
              cb.concat(personaJoin.get("nombres"), " "),
              personaJoin.get("apePaterno")
      );
      return cb.like(
              cb.upper(nombreCompleto),
              "%" + nombre.toUpperCase() + "%"
      );
    };
  }

  public static Specification<Venta> hasClienteDNI(String documento) {
    return (root, query, cb) -> {
      if (documento == null || documento.isBlank()) {
        return cb.conjunction();
      }
      Join<Venta, Cliente> clienteJoin = root.join("cliente", JoinType.INNER);
      Join<Cliente, Persona> personaJoin = clienteJoin.join("persona", JoinType.INNER);
      return cb.equal(personaJoin.get("numDocumento"), documento);
    };
  }

  public static Specification<Venta> hasDocumento(String documento) {
    return (root, query, cb) -> cb.equal(root.get("numDocumento"), documento);
  }

  public static Specification<Venta> hasSucursal(Sucursal sucursal) {
    return (root, query, cb) -> cb.equal(root.get("sucursal"), sucursal);
  }

  public static Specification<Venta> hasFechaBetween(LocalDate inicio, LocalDate fin) {
    return (root, query, cb) -> {
      if (inicio == null || fin == null) {
        return cb.conjunction();
      }
      return cb.between(root.get("fecha"), inicio, fin);
    };
  }

}
