package com.example.eureka.metricas.infrastructure.controller;


import com.example.eureka.metricas.domain.MetricasGenerales;
import com.example.eureka.metricas.domain.MetricasPregunta;
import com.example.eureka.metricas.port.in.MetricasGeneralesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/metricas-generales")
@RequiredArgsConstructor
public class MetricasGeneralesController {


    private final MetricasGeneralesService metricasGeneralesService;

    @GetMapping("/emprendimiento/mayor-vista")
    public ResponseEntity<MetricasGenerales> mayorVista() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByVistasDesc());
    }

    @GetMapping("/emprendimiento/menor-vista")
    public ResponseEntity<MetricasGenerales> menorVista() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByVistasAsc());
    }

    @GetMapping("/categoria/mayor-vista")
    public ResponseEntity<HashMap<String, Object>> mayorVistaCategoria() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByVistasCategoriaDesc());
    }

    @GetMapping("/emprendimiento/mayor-valoracion")
    public ResponseEntity<MetricasPregunta> mayorValoracion() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByNivelValoracionDesc());
    }

    @GetMapping("/emprendimiento/menor-valoracion")
    public ResponseEntity<MetricasPregunta> menorValoracion() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByNivelValoracionAsc());
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<MetricasGenerales>> filtros(
            @RequestParam(value = "fechaInicio", required = false) LocalDateTime fechaInicio,
            @RequestParam(value = "fechaFin", required = false) LocalDateTime fechaFin,
            @RequestParam(value = "idEmprendimiento", required = false) Integer idEmprendimiento) {

        return ResponseEntity.ok(metricasGeneralesService.findAllByFechaRegistroIsBetweenOrEmprendimientos(fechaInicio, fechaFin, idEmprendimiento));
    }

}
