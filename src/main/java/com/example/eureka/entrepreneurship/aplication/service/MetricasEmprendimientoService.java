package com.example.eureka.entrepreneurship.aplication.service;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.metricas.domain.MetricasGenerales;
import com.example.eureka.metricas.port.out.IMetricasGeneralesRepository;
import com.example.eureka.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricasEmprendimientoService {

    private final IMetricasGeneralesRepository metricasGeneralesRepository;

    public void registrarVistaPublica(Emprendimientos emprendimiento) {
        MetricasGenerales metricasGenerales =
                metricasGeneralesRepository.findByEmprendimientos(emprendimiento)
                        .orElse(null);

        if (metricasGenerales == null) {
            metricasGenerales = new MetricasGenerales();
            metricasGenerales.setEmprendimientos(emprendimiento);
            metricasGenerales.setVistas(1);
            metricasGenerales.setFechaRegistro(LocalDateTime.now());
        } else {
            if (metricasGenerales.getVistas() == null) {
                throw new BusinessException("Las vistas no pueden ser nulas para el emprendimiento " + emprendimiento.getId());
            }
            metricasGenerales.setVistas(metricasGenerales.getVistas() + 1);
        }

        metricasGeneralesRepository.save(metricasGenerales);
    }

    public void registrarVistaInterna(Emprendimientos emprendimiento) {
        MetricasGenerales metricasGenerales =
                metricasGeneralesRepository.findByEmprendimientos(emprendimiento)
                        .orElse(null);

        if (metricasGenerales == null) {
            metricasGenerales = new MetricasGenerales();
            metricasGenerales.setEmprendimientos(emprendimiento);
            metricasGenerales.setVistas(1);
            metricasGenerales.setFechaRegistro(LocalDateTime.now());
        } else {
            if (metricasGenerales.getVistas() == null) {
                throw new BusinessException("Las vistas no pueden ser nulas para el emprendimiento " + emprendimiento.getId());
            }
            metricasGenerales.setVistas(metricasGenerales.getVistas() + 1);
        }

        metricasGeneralesRepository.save(metricasGenerales);
    }
}
