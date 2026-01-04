package com.example.eureka.metricas.infrastructure.controller;

import com.example.eureka.metricas.domain.MetricasPregunta;
import com.example.eureka.metricas.infrastructure.dto.MetricasGeneralesDTO;
import com.example.eureka.metricas.infrastructure.dto.RankingGlobalDTO;
import com.example.eureka.metricas.infrastructure.dto.RankingPreguntaDTO;
import com.example.eureka.metricas.port.in.MetricasGeneralesService;
import com.example.eureka.metricas.port.in.MetricasPreguntaService;
import com.example.eureka.shared.util.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/metricas-generales")
@RequiredArgsConstructor
public class MetricasGeneralesController {


    private final MetricasGeneralesService metricasGeneralesService;
    private final MetricasPreguntaService metricasPreguntaService;

    @GetMapping("/emprendimientos/mas-vistos")
    public List<MetricasGeneralesDTO> getMasVistos() {
        return metricasGeneralesService.findAllOrderByVistasDesc();
    }

    @GetMapping("/emprendimientos/menos-vistos")
    public List<MetricasGeneralesDTO> getMenosVistos() {
        return metricasGeneralesService.findAllOrderByVistasAsc();
    }
    @GetMapping("/categoria/mayor-vista")
    public ResponseEntity<HashMap<String, Object>> mayorVistaCategoria() {
        return ResponseEntity.ok(metricasGeneralesService.findTopByOrderByVistasCategoriaDesc());
    }

    @GetMapping("/valoracion/asc")
    public List<RankingGlobalDTO> obtenerPorValoracionAsc() {
        return metricasPreguntaService.findAllOrderByValoracionAsc();
    }

    // Todas ordenadas de mayor a menor valoración
    @GetMapping("/valoracion/desc")
    public List<RankingGlobalDTO> obtenerPorValoracionDesc() {
        return metricasPreguntaService.findAllOrderByValoracionDesc();
    }

    // Ranking por pregunta (opcional: filtrar por tipo de emprendimiento)
    @GetMapping("/pregunta/{idPregunta}")
    public ResponseEntity<PageResponseDTO<RankingPreguntaDTO>> rankingPorPregunta(
            @PathVariable Long idPregunta,
            @RequestParam(required = false) Long idTipoEmprendimiento,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {

        Page<RankingPreguntaDTO> page =
                metricasPreguntaService.obtenerRankingPorPregunta(idPregunta, idTipoEmprendimiento, pageable);

        return ResponseEntity.ok(PageResponseDTO.fromPage(page));
    }

}
