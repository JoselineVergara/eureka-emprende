package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.general.dto.CategoriasDTO;

import lombok.Data;

@Data
public class EmprendimientoCategoriaDTO {

    private EmprendimientoDTO emprendimiento;

    private CategoriasDTO categoria;

    private String nombreCategoria;
}
