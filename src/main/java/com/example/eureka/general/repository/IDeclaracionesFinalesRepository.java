package com.example.eureka.general.repository;

import com.example.eureka.model.DeclaracionesFinales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeclaracionesFinalesRepository extends JpaRepository<DeclaracionesFinales,Integer> {
}
