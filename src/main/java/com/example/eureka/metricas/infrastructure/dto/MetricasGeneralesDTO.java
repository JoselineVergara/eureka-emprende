package com.example.eureka.metricas.infrastructure.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetricasGeneralesDTO {

    private Integer id;
    private Integer idEmprendimiento;
    private String nombreEmprendimiento;
    private Integer vistas;
    private LocalDateTime fechaRegistro;

}
