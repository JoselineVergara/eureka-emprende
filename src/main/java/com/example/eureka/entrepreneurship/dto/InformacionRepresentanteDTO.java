package com.example.eureka.entrepreneurship.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionRepresentanteDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String correoCorporativo;
    private String correoPersonal;
    private String telefono;
    private String identificacion;
    private String carrera;
    private String semestre;
    private LocalDateTime fechaGraduacion;
    private Boolean tieneParientesUees;
    private String nombrePariente;
    private String areaPariente;
    private String integrantesEquipo;
    private Integer emprendimientoId;
}
