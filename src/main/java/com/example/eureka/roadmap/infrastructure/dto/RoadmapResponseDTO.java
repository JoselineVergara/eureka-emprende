package com.example.eureka.roadmap.infrastructure.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoadmapResponseDTO {
    private Integer id;
    private Integer idEmprendimiento;
    private List<RoadmapFaseDTO> contenido;
    private String historia;
    private String objetivo;
}
