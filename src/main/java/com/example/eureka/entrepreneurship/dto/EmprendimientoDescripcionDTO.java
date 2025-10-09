package com.example.eureka.entrepreneurship.dto;

import lombok.Data;

@Data
public class EmprendimientoDescripcionDTO {

    private String tipoDescripcion;

    private String descripcion;

    private Integer maxCaracteres;

    private Boolean obligatorio;

    private Integer idEmprendimiento;
}
