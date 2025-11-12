package com.example.eureka.formulario.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaResponseDTO {
    private Long idPregunta;
    private String pregunta;
    private String tipo;
    private Integer numeroRespuestas;
    private Boolean obligatoria;
    private Integer orden;
    private List<OpcionResponseDTO> opciones;
}