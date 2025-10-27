package com.example.eureka.auth.dto;

import com.example.eureka.enums.Genero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioPerfilDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private Genero genero;
    private String correo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaNacimiento;
    private Boolean activo;
    private String nombreRol;
    private Integer idRol;
}
