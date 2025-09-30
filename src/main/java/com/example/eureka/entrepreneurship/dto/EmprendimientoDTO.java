package com.example.eureka.entrepreneurship.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmprendimientoDTO {
    //datos para emprendimiento
    private String correoComercial;
    private String correoUees;
    private String identificacion;
    private String parienteDirecto;
    private String nombreComercialEmprendimiento;
    private Date fechaCreacion;
    private Integer ciudad;
    private Integer provinia;
    private Boolean estadoEmpredimiento;
    private String tipoEmprendimiento;

}
