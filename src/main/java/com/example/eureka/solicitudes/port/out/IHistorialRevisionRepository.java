package com.example.eureka.solicitudes.port.out;

import com.example.eureka.solicitudes.domain.model.HistorialRevision;
import com.example.eureka.solicitudes.domain.model.SolicitudAprobacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistorialRevisionRepository extends JpaRepository<HistorialRevision, Long> {

    List<HistorialRevision> findBySolicitudIdOrderByFechaAccionDesc(Integer solicitudId);
}