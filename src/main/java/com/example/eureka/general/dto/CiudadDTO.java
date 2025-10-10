package com.example.eureka.general.dto;

import lombok.Data;

@Data
public class CiudadDTO {

    private Integer id;
    private String nombreCiudad;
    private ProvinciaDTO provincia;
}
