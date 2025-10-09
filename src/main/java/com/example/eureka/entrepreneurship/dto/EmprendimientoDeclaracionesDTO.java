package com.example.eureka.entrepreneurship.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EmprendimientoDeclaracionesDTO {

    private Integer emprendimientoId;

    private Integer declaracionId;

    private Boolean aceptada;

    private LocalDateTime fechaAceptacion;

    private String nombreFirma;
}
