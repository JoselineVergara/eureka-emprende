package com.example.eureka.roadmap.application.service;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.domain.model.DescripcionEmprendimiento;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosDescripcionRepository;
import com.example.eureka.roadmap.domain.model.Roadmap;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosRepository;
import com.example.eureka.roadmap.domain.service.RoadmapDescripcionBuilder;
import com.example.eureka.roadmap.infrastructure.dto.RoadmapDTO;
import com.example.eureka.roadmap.port.out.IRoadmapRepository;
import com.example.eureka.roadmap.port.in.RoadmapService;
import com.example.eureka.shared.config.openapi.GPTRoadmap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoadmapServiceImpl implements RoadmapService {

    private final IRoadmapRepository roadmapRepository;
    private final GPTRoadmap gptRoadmap;
    private final IEmprendimientosRepository  emprendimientosRepository;
    private final IEmprendimientosDescripcionRepository emprendimientosDescripcionRepository;
    private final RoadmapDescripcionBuilder descripcionBuilder = new RoadmapDescripcionBuilder();

    @Override
    public Roadmap findByIdCompany(Integer id) {
        Emprendimientos emp =  emprendimientosRepository.findById(id).orElse(null);
        return roadmapRepository.findByEmprendimiento(emp);
    }

    @Override
    public Roadmap findById(Integer id) {
        return roadmapRepository.findById(id).orElse(null);
    }

    @Override
    public Roadmap save(RoadmapDTO roadmap) {

        Emprendimientos emp = emprendimientosRepository
                .findById(roadmap.getIdEmprendimiento())
                .orElse(null);

        List<DescripcionEmprendimiento> descripcionEmprendimientos =
                emprendimientosDescripcionRepository
                        .findByEmprendimientoId(roadmap.getIdEmprendimiento());

        String historia = descripcionBuilder.construirHistoria(descripcionEmprendimientos);
        String objetivo = descripcionBuilder.construirObjetivo(descripcionEmprendimientos);

        roadmap.setHistoria(historia);
        roadmap.setObjetivo(objetivo);

        Roadmap rm;

        if (!roadmapRepository.existsByEmprendimiento(emp)) {
            // Crear roadmap nuevo
            String contenido = gptRoadmap.generarRoadmap(historia, objetivo);

            rm = new Roadmap();
            rm.setEmprendimiento(emp);
            rm.setHistoria(historia);
            rm.setObjetivo(objetivo);
            rm.setContenido(contenido);
            rm.setFechaCreacion(LocalDateTime.now());
        } else {
            rm = roadmapRepository.findByEmprendimiento(emp);

            boolean cambioHistoria = historia != null
                    ? !historia.equals(rm.getHistoria())
                    : rm.getHistoria() != null;

            boolean cambioObjetivo = objetivo != null
                    ? !objetivo.equals(rm.getObjetivo())
                    : rm.getObjetivo() != null;

            if (cambioHistoria || cambioObjetivo) {
                rm.setHistoria(historia);
                rm.setObjetivo(objetivo);

                String contenido = gptRoadmap.generarRoadmap(historia, objetivo);
                rm.setContenido(contenido);
            }
        }

        return roadmapRepository.save(rm);
    }



    @Override
    public Page<Roadmap> findAll(Pageable pageable) {
        return roadmapRepository.findAll(pageable);
    }
}
