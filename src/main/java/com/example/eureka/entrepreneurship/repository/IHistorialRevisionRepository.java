package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.HistorialRevision;
import com.example.eureka.model.SolicitudAprobacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistorialRevisionRepository extends JpaRepository<HistorialRevision, Long> {

    List<HistorialRevision> findBySolicitudOrderByFechaAccionDesc(SolicitudAprobacion solicitud);

    List<HistorialRevision> findBySolicitudIdOrderByFechaAccionDesc(Long solicitudId);
}