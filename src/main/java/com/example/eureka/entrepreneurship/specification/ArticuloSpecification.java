package com.example.eureka.entrepreneurship.specification;

import com.example.eureka.enums.EstadoArticulo;
import com.example.eureka.model.ArticulosBlog;
import com.example.eureka.model.TagsBlog;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticuloSpecification {

    public static Specification<ArticulosBlog> conFiltros(
            EstadoArticulo estado,
            Integer idTag,
            String titulo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por estado (solo si no es null)
            if (estado != null) {
                predicates.add(criteriaBuilder.equal(root.get("estado"), estado));
            }

            // Filtro por tag (solo si no es null)
            if (idTag != null) {
                Join<ArticulosBlog, TagsBlog> tagsJoin = root.join("tags", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(tagsJoin.get("idTag"), idTag));
            }

            // Filtro por título (solo si no es null y no está vacío)
            if (titulo != null && !titulo.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titulo")),
                        "%" + titulo.toLowerCase() + "%"
                ));
            }

            // Filtro por fecha inicio (solo si no es null)
            if (fechaInicio != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("fechaCreacion"), fechaInicio
                ));
            }

            // Filtro por fecha fin (solo si no es null)
            if (fechaFin != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("fechaCreacion"), fechaFin
                ));
            }

            // DISTINCT para evitar duplicados por el JOIN con tags
            query.distinct(true);

            // Ordenar por fecha de creación descendente
            query.orderBy(criteriaBuilder.desc(root.get("fechaCreacion")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}