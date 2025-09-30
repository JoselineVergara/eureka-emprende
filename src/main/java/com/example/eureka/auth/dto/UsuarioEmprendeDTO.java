package com.example.eureka.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UsuarioEmprendeDTO {

    //datos usuario
    private String nombre;
    private String apellido;
    private String correo;//este es el correo corporativo
    private String contrasena;
    private String genero;
    private Date fechaRegistro;
    private Integer idRol;
    private Date fechaNacimiento; //falta

    //datos para emprendimiento
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
