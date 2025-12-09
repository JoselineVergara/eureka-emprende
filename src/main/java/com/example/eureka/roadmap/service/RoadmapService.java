package com.example.eureka.roadmap.service;

import com.example.eureka.domain.model.Roadmap;
import com.example.eureka.roadmap.dto.RoadmapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoadmapService {

    Roadmap findByIdCompany(Integer id);
    Roadmap findById(Integer id);
    Roadmap save(RoadmapDTO roadmap);
    Page<Roadmap> findAll(Pageable pageable);

}
