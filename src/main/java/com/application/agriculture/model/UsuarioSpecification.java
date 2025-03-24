package com.application.agriculture.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import com.application.agriculture.dto.*;

public class UsuarioSpecification {
    public static Specification<Usuario> filtrar(DadosFiltroUsuario filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.nome() != null && !filtro.nome().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + filtro.nome().toLowerCase() + "%"));
            }
            if (filtro.email() != null && !filtro.email().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filtro.email().toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
