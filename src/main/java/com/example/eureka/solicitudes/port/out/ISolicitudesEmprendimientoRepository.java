package com.example.eureka.solicitudes.port.out;

import com.example.eureka.solicitudes.domain.model.SolicitudEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISolicitudesEmprendimientoRepository extends JpaRepository<SolicitudEmprendimiento,Integer> {
}
