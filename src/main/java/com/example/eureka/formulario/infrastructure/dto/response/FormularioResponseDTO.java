package com.example.eureka.formulario.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioResponseDTO {
    private Long idFormulario;
    private String nombre;
    private String tipoFormulario;
    private List<PreguntaResponseDTO> preguntas;
}