package com.example.eureka.event.infrastructure.persistence;

import com.example.eureka.event.domain.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoJpaRepository extends JpaRepository<Eventos, Integer>,
        JpaSpecificationExecutor<Eventos> {
    // Tus métodos personalizados si los tienes
}