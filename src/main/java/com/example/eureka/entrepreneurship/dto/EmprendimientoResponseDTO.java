package com.example.eureka.entrepreneurship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoResponseDTO {

    private Integer id;
    private String nombreComercial;
    private Date anioCreacion;
    private Boolean activoEmprendimiento;
    private Boolean aceptaDatosPublicos;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estadoEmprendimiento;

    // Relaciones simplificadas (solo nombres o identificadores)
    private Integer usuarioId;
    private String nombreUsuario;

    private Integer ciudadId;
    private String nombreCiudad;

    private Integer tipoEmprendimientoId;
    private String nombreTipoEmprendimiento;
}
