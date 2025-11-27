package com.example.eureka.valoracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValoracionDTO {

    private Integer id;
    private String nombre;
    private String categoria;
    private String tipo;
    private String fecha;
    private String hora;
    private String estado;

}
