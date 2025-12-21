package com.example.eureka.entrepreneurship.infrastructure.dto.response;

import lombok.Data;

@Data
public class CategoriaListadoDTO {
    private Integer id;
    private String nombre;

    public CategoriaListadoDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
