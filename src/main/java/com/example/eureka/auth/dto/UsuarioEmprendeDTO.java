package com.example.eureka.auth.dto;

import com.example.eureka.entrepreneurship.dto.EmprendimientoDTO;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class UsuarioEmprendeDTO {

    //datos usuario parte 1
    @NonNull
    private String nombre;
    @NonNull
    private String apellido;
    @NonNull
    private Date fechaNacimiento; //falta
    @NonNull
    private String genero;
    @NonNull
    private String contrasena;

    //datos usuario parte 2
    @NonNull
    private String correo;
    @NonNull
    private String correoUees;
    @NonNull
    private String identificacion;
    @NonNull
    private String parienteDirecto;

    private Integer idRol;

    private String nombrePariente;

    private String carrera;

    private String anioEstudio;

    private EmprendimientoDTO emprendimiento;
}
