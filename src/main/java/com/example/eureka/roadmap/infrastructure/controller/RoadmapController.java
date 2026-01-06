package com.example.eureka.roadmap.infrastructure.controller;


import com.example.eureka.roadmap.domain.model.Roadmap;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapDTO;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapResponseDTO;
import com.example.eureka.roadmap.infrastructure.mapper.RoadmapMapper;
import com.example.eureka.roadmap.port.in.RoadmapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/roadmap")
@RequiredArgsConstructor
public class RoadmapController {

    private final RoadmapService  roadmapService;
    private final RoadmapMapper roadmapMapper;

    @GetMapping
    public ResponseEntity<Page<Roadmap>> findAll(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        return ResponseEntity.ok(roadmapService.findAll(pageable));
    }

    @GetMapping("/emprendimiento/{id}")
    public ResponseEntity<RoadmapResponseDTO> findAllByEmprendimientoId(@PathVariable Integer id) {
        Roadmap rm = roadmapService.findByIdCompany(id);
        RoadmapResponseDTO dto = roadmapMapper.toDto(rm);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/save")
    public ResponseEntity<RoadmapResponseDTO> save(@RequestBody RoadmapDTO roadmap) {
        Roadmap rm = roadmapService.save(roadmap);
        RoadmapResponseDTO dto = roadmapMapper.toDto(rm);
        return ResponseEntity.ok(dto);
    }





}
