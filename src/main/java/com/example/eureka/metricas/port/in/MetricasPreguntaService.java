package com.example.eureka.metricas.port.in;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.formulario.domain.model.Pregunta;
import com.example.eureka.metricas.domain.MetricasPregunta;

import java.util.List;

public interface MetricasPreguntaService {

    MetricasPregunta save(MetricasPregunta metricasPregunta);
    List<MetricasPregunta> findAll();
    MetricasPregunta findById(Integer id);
    void deleteById(Integer id);
    List<MetricasPregunta> findAllByEmprendimientosAndPregunta(Emprendimientos emprendimientos, Pregunta pregunta);
}
