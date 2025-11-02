package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventosRepository extends JpaRepository<Eventos, Integer>,
        JpaSpecificationExecutor<Eventos> {
}