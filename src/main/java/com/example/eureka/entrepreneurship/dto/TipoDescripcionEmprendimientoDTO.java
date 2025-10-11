package com.example.eureka.entrepreneurship.dto;

import lombok.Data;

@Data
public class TipoDescripcionEmprendimientoDTO {

    private Integer id;
    private String tipoDescripcion;
    private String descripcion;
    private Integer maxCaracteres;
    private Boolean obligatorio;
    private EmprendimientoResponseDTO emprendimiento;

}
