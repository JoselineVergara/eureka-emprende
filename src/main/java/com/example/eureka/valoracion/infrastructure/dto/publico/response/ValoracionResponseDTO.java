package com.example.eureka.valoracion.infrastructure.dto.publico.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValoracionResponseDTO {
    private Long idRespuesta;
    private String mensaje;
    private Double promedioObtenido;
    private Boolean requiereAutoevaluacion;
}