package com.svc.ventas.models.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.svc.ventas.models.entity.Cliente;
import com.svc.ventas.models.entity.Persona;

public class ClienteSpecifications {

    public static Specification<Cliente> hasClienteNombre(String nombre) {
        return (root, query, criteriaBuilder) -> {
            Join<Cliente, Persona> personaJoin = root.join("persona", JoinType.INNER);
            return criteriaBuilder.equal(personaJoin.get("nombre"), nombre);
        };
    }

    public static Specification<Cliente> hasClienteDocumento(String documento) {
        return (root, query, criteriaBuilder) -> {
            Join<Cliente, Persona> personaJoin = root.join("persona", JoinType.INNER);
            return  criteriaBuilder.equal(personaJoin.get("numDocumento"), documento);
        };

    }
}
