package com.example.eureka.metricas.port.out;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.formulario.domain.model.Pregunta;
import com.example.eureka.metricas.domain.MetricasPregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMetricasPreguntaRepository extends JpaRepository<MetricasPregunta, Integer> {

    List<MetricasPregunta> findAllByEmprendimientosAndPregunta(Emprendimientos emprendimientos, Pregunta pregunta);

    MetricasPregunta findTopByOrderByValoracionDesc();

    MetricasPregunta findTopByOrderByValoracionAsc();

}
