package com.example.eureka.metricas.port.in;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.metricas.domain.MetricasGenerales;
import com.example.eureka.metricas.domain.MetricasPregunta;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface MetricasGeneralesService {

    MetricasGenerales findTopByOrderByVistasDesc();

    MetricasGenerales findTopByOrderByVistasAsc();

    MetricasPregunta findTopByOrderByNivelValoracionDesc();

    MetricasPregunta findTopByOrderByNivelValoracionAsc();

    MetricasGenerales save(MetricasGenerales metricasGenerales);

    MetricasGenerales findById(Integer id);

    MetricasGenerales findByIdEmprendimiento(Integer id);

    HashMap<String, Object> findTopByOrderByVistasCategoriaDesc();

    List<MetricasGenerales> findAllByFechaRegistroIsBetweenOrEmprendimientos(LocalDateTime fechaRegistroAfter, LocalDateTime fechaRegistroBefore, Integer idEmprendimientos);

}
