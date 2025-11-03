package com.example.eureka.entrepreneurship.dto.shared;

import com.example.eureka.general.dto.CategoriasDTO;

import lombok.Data;

@Data
public class EmprendimientoCategoriaDTO {

    private EmprendimientoDTO emprendimiento;

    private CategoriasDTO categoria;

    private String nombreCategoria;
}
