package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.DeclaracionesFinales;
import com.example.eureka.model.EmprendimientoDeclaraciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientoDeclaracionesRepository extends JpaRepository<EmprendimientoDeclaraciones, Integer> {
}
