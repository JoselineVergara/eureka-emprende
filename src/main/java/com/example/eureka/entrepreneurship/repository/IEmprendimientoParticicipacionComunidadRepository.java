package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.EmprendimientoParticipacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientoParticicipacionComunidadRepository extends JpaRepository<EmprendimientoParticipacion, Integer> {
    List<EmprendimientoParticipacion> findByEmprendimientoId(Integer emprendimientoId);
}
