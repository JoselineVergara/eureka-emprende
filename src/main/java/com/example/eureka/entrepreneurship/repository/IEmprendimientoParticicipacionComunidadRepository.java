package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.EmprendimientoParticipacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientoParticicipacionComunidadRepository extends JpaRepository<EmprendimientoParticipacion, Integer> {
    List<EmprendimientoParticipacion> findByEmprendimientoId(Integer emprendimientoId);

    @org.springframework.data.jpa.repository.Query("SELECT ep FROM EmprendimientoParticipacion ep JOIN FETCH ep.opcionParticipacion WHERE ep.emprendimiento.id = :emprendimientoId")
    java.util.List<EmprendimientoParticipacion> findByEmprendimientoIdFetchOpcion(@org.springframework.data.repository.query.Param("emprendimientoId") Integer emprendimientoId);
}
