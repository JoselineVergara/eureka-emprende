package com.example.eureka.general.dto;

import lombok.Data;

@Data
public class DescripcionesDTO {

    private Integer id;
    private String descripcion;
    private Integer cantidadMaximaCaracteres;
    private Boolean estado;
}
