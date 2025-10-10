package com.example.eureka.dto;

import lombok.*;
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
    private Date fechaGraduacion;
    private Boolean tieneParientesUees;
    private String nombrePariente;
    private String areaPariente;
    private String integrantesEquipo;
    private Integer emprendimientoId; 
}
