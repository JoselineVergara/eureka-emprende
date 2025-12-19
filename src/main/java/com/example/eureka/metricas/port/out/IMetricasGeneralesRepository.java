package com.example.eureka.metricas.port.out;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.metricas.domain.MetricasBasicas;
import com.example.eureka.metricas.domain.MetricasGenerales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMetricasGeneralesRepository extends JpaRepository<MetricasGenerales, Integer> {

    Optional<MetricasGenerales> findTopByOrderByVistasDesc();

    Optional<MetricasGenerales> findTopByOrderByVistasAsc();

    Optional<MetricasGenerales> findByEmprendimientos(Emprendimientos emprendimientos);

    List<MetricasGenerales> findAllByFechaRegistroIsBetweenOrEmprendimientos(LocalDateTime fechaRegistroAfter, LocalDateTime fechaRegistroBefore, Emprendimientos emprendimientos);

    @Query("""
       SELECT m
       FROM MetricasGenerales m
       WHERE (:fechaInicio IS NULL OR m.fechaRegistro >= :fechaInicio)
         AND (:fechaFin IS NULL OR m.fechaRegistro <= :fechaFin)
         AND (:emprendimiento IS NULL OR m.emprendimientos = :emprendimiento)
       """)
    List<MetricasGenerales> findByFiltrosOpcionales(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("emprendimiento") Emprendimientos emprendimiento
    );


}
