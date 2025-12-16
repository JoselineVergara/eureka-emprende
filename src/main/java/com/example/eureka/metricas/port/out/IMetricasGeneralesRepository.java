package com.example.eureka.metricas.port.out;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.metricas.domain.MetricasBasicas;
import com.example.eureka.metricas.domain.MetricasGenerales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMetricasGeneralesRepository extends JpaRepository<MetricasGenerales, Integer> {

    Optional<MetricasGenerales> findTopByOrderByVistasDesc();

    Optional<MetricasGenerales> findTopByOrderByVistasAsc();

    Optional<MetricasGenerales> findTopByOrderByNivelValoracionDesc();

    Optional<MetricasGenerales> findTopByOrderByNivelValoracionAsc();

    Optional<MetricasGenerales> findByEmprendimientos(Emprendimientos emprendimientos);

    List<MetricasGenerales> findAllByFechaRegistroIsBetweenOrEmprendimientos(LocalDateTime fechaRegistroAfter, LocalDateTime fechaRegistroBefore, Emprendimientos emprendimientos);
}
