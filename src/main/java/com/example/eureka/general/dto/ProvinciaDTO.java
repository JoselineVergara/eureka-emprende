package com.example.eureka.general.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ProvinciaDTO {

    private Integer id;
    private String nombre;
    private Boolean activo;
}
