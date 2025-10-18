package com.example.eureka.auth.dto;

import com.example.eureka.entrepreneurship.dto.EmprendimientoDTO;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UsuarioEmprendeDTO {

    //datos usuario parte 1
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento; //falta
    private String genero;
    private String contrasena;

    //datos usuario parte 2
    private String correo;
    private String correoUees;
    private String identificacion;
    private Boolean parienteDirecto;

    private Integer idRol;

    private String nombrePariente;

    private String areaPariente;

    private String carrera;

    private LocalDateTime fechaGraduacion;

    private String anioEstudio;

    private String semestre;

    private EmprendimientoDTO emprendimiento;
}
