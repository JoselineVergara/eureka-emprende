package com.example.eureka.valoracion.infrastructure.dto.emprendedor.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoevaluacionResponseDTO {

    private Long idRespuesta;
    private Integer idEmprendimiento;
    private String nombreEmprendimiento;
    private LocalDateTime fechaRespuesta;
    private Long idValoracionOrigen;
    private List<PreguntaRespuestaDTO> preguntasRespuestas;

}