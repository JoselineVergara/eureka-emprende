package com.example.eureka.general.service.impl;

import com.example.eureka.general.dto.DeclaracionesFinalesDTO;
import com.example.eureka.general.repository.IDeclaracionesFinalesRepository;
import com.example.eureka.general.service.IDeclaracionesFinalesService;
import com.example.eureka.model.DeclaracionesFinales;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeclaracionesFinalesServiceImpl implements IDeclaracionesFinalesService {

    private final IDeclaracionesFinalesRepository repository;

    private DeclaracionesFinalesDTO mapToDTO(DeclaracionesFinales entity) {
        DeclaracionesFinalesDTO dto = new DeclaracionesFinalesDTO();
        dto.setId(entity.getId());
        dto.setDeclaracion(entity.getDeclaracion());
        dto.setObligatoria(entity.getObligatoria());
        return dto;
    }

    // Conversión DTO → Entity
    private DeclaracionesFinales mapToEntity(DeclaracionesFinalesDTO dto) {
        DeclaracionesFinales entity = new DeclaracionesFinales();
        entity.setId(dto.getId());
        entity.setDeclaracion(dto.getDeclaracion());
        entity.setObligatoria(dto.getObligatoria());
        return entity;
    }

    @Override
    public List<DeclaracionesFinalesDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeclaracionesFinalesDTO obtenerPorId(Integer id) {
        DeclaracionesFinales entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Declaración final no encontrada"));
        return mapToDTO(entity);
    }

    @Override
    public DeclaracionesFinalesDTO guardar(DeclaracionesFinalesDTO dto) {
        DeclaracionesFinales entity = mapToEntity(dto);
        DeclaracionesFinales guardada = repository.save(entity);
        return mapToDTO(guardada);
    }

    @Override
    public DeclaracionesFinalesDTO actualizar(Integer id, DeclaracionesFinalesDTO dto) {
        DeclaracionesFinales existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Declaración final no encontrada"));

        existente.setDeclaracion(dto.getDeclaracion());
        existente.setObligatoria(dto.getObligatoria());

        DeclaracionesFinales actualizada = repository.save(existente);
        return mapToDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Declaración final no encontrada");
        }
        repository.deleteById(id);
    }
}
