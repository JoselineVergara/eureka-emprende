package com.example.eureka.metricas.infrastructure.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetricaPreguntaDTO {

    private Integer id;
    private Integer idEmprendimiento;
    private Integer idPregunta;
    private Double valoracion;
    private LocalDateTime fechaRegistro;

}
