package com.example.eureka.roadmap.domain.service;

import com.example.eureka.entrepreneurship.domain.model.DescripcionEmprendimiento;

import java.util.List;

public class RoadmapDescripcionBuilder {

    public static final int DESC_HISTORIA = 2;
    public static final int DESC_PROPOSITO = 5;

    public String construirHistoria(List<DescripcionEmprendimiento> descripciones) {
        String historia = "";
        for (DescripcionEmprendimiento e : descripciones) {
            if (e.getDescripciones() == null ||
                    e.getDescripciones().getId() == null ||
                    e.getRespuesta() == null ||
                    e.getRespuesta().isBlank()) {
                continue;
            }
            if (e.getDescripciones().getId() == DESC_HISTORIA) {
                historia = (historia + " " + e.getRespuesta()).trim();
            }
        }
        return historia;
    }

    public String construirObjetivo(List<DescripcionEmprendimiento> descripciones) {
        String objetivo = "";
        for (DescripcionEmprendimiento e : descripciones) {
            if (e.getDescripciones() == null ||
                    e.getDescripciones().getId() == null ||
                    e.getRespuesta() == null ||
                    e.getRespuesta().isBlank()) {
                continue;
            }
            if (e.getDescripciones().getId() == DESC_PROPOSITO) {
                objetivo = (objetivo + " " + e.getRespuesta()).trim();
            }
        }
        return objetivo;
    }
}
