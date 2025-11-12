package com.example.eureka.valoracion.infrastructure.dto.emprendedor.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoevaluacionRequestDTO {

    @NotNull(message = "El ID del emprendimiento es obligatorio")
    private Integer idEmprendimiento;

    @NotNull(message = "El ID del formulario es obligatorio")
    private Long idFormulario;

    @NotNull(message = "La valoración de origen es obligatoria")
    private Long idValoracionOrigen;

    @NotEmpty(message = "Debe proporcionar al menos una respuesta")
    @Valid
    private List<RespuestaPreguntaDTO> respuestas;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RespuestaPreguntaDTO {

        @NotNull(message = "El ID de la pregunta es obligatorio")
        private Long idPregunta;

        private List<Long> idsOpciones;
        private Integer valorEscala;
    }
}