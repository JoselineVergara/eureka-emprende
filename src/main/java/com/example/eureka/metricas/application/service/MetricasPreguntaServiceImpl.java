package com.example.eureka.metricas.application.service;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.formulario.domain.model.Pregunta;
import com.example.eureka.metricas.domain.MetricasBasicas;
import com.example.eureka.metricas.domain.MetricasPregunta;
import com.example.eureka.metricas.port.in.MetricasBasicasService;
import com.example.eureka.metricas.port.in.MetricasPreguntaService;
import com.example.eureka.metricas.port.out.IMetricasPreguntaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricasPreguntaServiceImpl implements MetricasPreguntaService {

    private final IMetricasPreguntaRepository metricasPreguntaRepository;


    @Override
    public MetricasPregunta save(MetricasPregunta metricasPregunta) {
        return metricasPreguntaRepository.save(metricasPregunta);
    }

    @Override
    public List<MetricasPregunta> findAll() {
        return metricasPreguntaRepository.findAll();
    }

    @Override
    public MetricasPregunta findById(Integer id) {
        return metricasPreguntaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        metricasPreguntaRepository.deleteById(id);
    }

    @Override
    public List<MetricasPregunta> findAllByEmprendimientosAndPregunta(Emprendimientos emprendimientos, Pregunta pregunta) {
        return metricasPreguntaRepository.findAllByEmprendimientosAndPregunta(emprendimientos, pregunta);
    }
}
