package com.example.eureka.roadmap.port.in;

import com.example.eureka.roadmap.domain.model.Roadmap;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoadmapService {

    Roadmap findByIdCompany(Integer id);
    Roadmap findById(Integer id);
    Roadmap save(RoadmapDTO roadmap);
    Page<Roadmap> findAll(Pageable pageable);

}
