package com.example.eureka.auth.dto;

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
    private String correo; //este es el correo corporativo
    @NonNull
    private String correoUees;
    @NonNull
    private String identificacion;
    @NonNull
    private String parienteDirecto;

    //datos para emprendimiento
    @NonNull
    private String nombreComercialEmprendimiento;
    @NonNull
    private Date fechaCreacion;
    @NonNull
    private Integer provincia;
    @NonNull
    private Integer ciudad;
    @NonNull
    private Boolean estadoEmprendimiento;
    @NonNull
    private Integer tipoEmprendimiento;

    private Date fechaRegistro;

    private Integer idRol;

    private String nombrePariente;

    private String carrera;

    private String anioEstudio;
}
