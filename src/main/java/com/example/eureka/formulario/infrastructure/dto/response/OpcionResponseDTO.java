package com.example.eureka.formulario.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionResponseDTO {
    private Long idOpcion;
    private String opcion;
}