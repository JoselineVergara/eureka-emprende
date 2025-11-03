package com.example.eureka.entrepreneurship.dto.shared;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmprendimientoDeclaracionesDTO {

    private Integer emprendimientoId;

    private Integer declaracionId;

    private Boolean aceptada;

    private LocalDateTime fechaAceptacion;

    private String nombreFirma;
}
