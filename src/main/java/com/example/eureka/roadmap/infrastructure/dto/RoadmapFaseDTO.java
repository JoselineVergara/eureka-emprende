package com.example.eureka.roadmap.infrastructure.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoadmapFaseDTO {
    private String fase;
    private List<String> ideas;
}
