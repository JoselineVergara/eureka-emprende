package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.EmprendimientoDeclaraciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientoDeclaracionesRepository extends JpaRepository<EmprendimientoDeclaraciones, Integer> {
    List<EmprendimientoDeclaraciones> findByEmprendimientoId(Integer emprendimientoId);
    List<EmprendimientoDeclaraciones> findByEmprendimientoIdIn(List<Integer> emprendimientoIds);

}
