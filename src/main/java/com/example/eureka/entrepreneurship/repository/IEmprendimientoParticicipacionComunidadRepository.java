package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.EmprendimientoParticipacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientoParticicipacionComunidadRepository extends JpaRepository<EmprendimientoParticipacion, Integer> {
}
