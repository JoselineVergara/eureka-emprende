package com.example.eureka.roadmap.infrastructure.mapper;

import com.example.eureka.roadmap.domain.model.Roadmap;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapFaseDTO;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class RoadmapMapper {

    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(RoadmapMapper.class);

    public RoadmapResponseDTO toDto(Roadmap rm) {
        RoadmapResponseDTO dto = new RoadmapResponseDTO();

        dto.setId(rm.getId());
        dto.setIdEmprendimiento(
                rm.getEmprendimiento() != null ? rm.getEmprendimiento().getId() : null
        );
        dto.setHistoria(rm.getHistoria());
        dto.setObjetivo(rm.getObjetivo());

        try {
            log.info("Contenido en BD: {}", rm.getContenido());
            List<RoadmapFaseDTO> fases = objectMapper.readValue(
                    rm.getContenido(),
                    new TypeReference<List<RoadmapFaseDTO>>() {}
            );
            dto.setContenido(fases);
        } catch (Exception e) {
            log.error("Error parseando contenido de roadmap id={}", rm.getId(), e);
            dto.setContenido(Collections.emptyList());
        }

        return dto;
    }
}
