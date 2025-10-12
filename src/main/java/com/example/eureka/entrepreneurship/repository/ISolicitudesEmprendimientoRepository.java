package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.SolicitudEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISolicitudesEmprendimientoRepository extends JpaRepository<SolicitudEmprendimiento,Integer> {
}
