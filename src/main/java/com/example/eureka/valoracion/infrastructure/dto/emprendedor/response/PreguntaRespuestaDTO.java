package com.example.eureka.valoracion.infrastructure.dto.emprendedor.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaRespuestaDTO {

        private Long idPregunta;
        private String textoPregunta;
        private String tipoPregunta;
        private List<OpcionSeleccionadaDTO> opcionesSeleccionadas;
        private Integer valorEscala;
}