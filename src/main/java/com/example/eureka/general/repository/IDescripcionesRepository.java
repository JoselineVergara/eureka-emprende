package com.example.eureka.general.repository;

import com.example.eureka.model.Descripciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDescripcionesRepository extends JpaRepository<Descripciones,Integer> {
}
