package com.svc.ventas.models.specifications;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.entity.Empleado;
import com.svc.ventas.models.entity.Persona;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EmpleadoSpecifications {
  public static Specification<Empleado> hasNombre(String nombre) {
    return (root, query, cb) -> {
      Join<Empleado, Persona> personaJoin = root.join("persona", JoinType.INNER);

      Expression<String> nombreCompleto = cb.concat(
              cb.concat(personaJoin.get("nombre"), " "), root.get("apePaterno")
      );
      return cb.like(
              cb.upper(nombreCompleto),
              "%" + nombre.toUpperCase() + "%"
      );
    };
  }

  public static Specification<Empleado> hasDocumento(String documento) {
    return (root, query, cb) -> {
      Join<Empleado, Persona> personaJoin = root.join("persona", JoinType.INNER);
      return cb.equal(personaJoin.get("numDocumento"), documento);
    };
  }
}
