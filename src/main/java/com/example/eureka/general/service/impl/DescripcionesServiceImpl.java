package com.example.eureka.general.service.impl;

import com.example.eureka.general.dto.DescripcionesDTO;
import com.example.eureka.general.dto.OpcionesParticipacionComunidadDTO;
import com.example.eureka.general.dto.converter.DescripcionesDtoConverter;
import com.example.eureka.general.repository.IDescripcionesRepository;
import com.example.eureka.general.service.IDescripcionesService;
import com.example.eureka.model.Descripciones;
import com.example.eureka.model.OpcionesParticipacionComunidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DescripcionesServiceImpl implements IDescripcionesService {

    private final IDescripcionesRepository repository;

    private final DescripcionesDtoConverter converter;


    @Override
    public List<DescripcionesDTO> listar() {
        return repository.findAll()
                .stream()
                .map(entity -> {
                    DescripcionesDTO dto = new DescripcionesDTO();
                    dto.setId(entity.getId());
                    dto.setDescripcion(entity.getDescripcion());
                    dto.setEstado(entity.getEsActivo());
                    dto.setCantidadMaximaCaracteres(entity.getCantidadMaximaCaracteres());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public DescripcionesDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(entity -> {
                    DescripcionesDTO dto = new DescripcionesDTO();
                    dto.setId(entity.getId());
                    dto.setDescripcion(entity.getDescripcion());
                    dto.setEstado(entity.getEsActivo());
                    dto.setCantidadMaximaCaracteres(entity.getCantidadMaximaCaracteres());
                    return dto;
                })
                .orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DescripcionesDTO guardar(DescripcionesDTO dto) {
        Descripciones entity = new Descripciones();
        entity.setDescripcion(dto.getDescripcion());
        entity.setEsActivo(dto.getEstado());
        entity.setCantidadMaximaCaracteres(dto.getCantidadMaximaCaracteres());

        Descripciones saved = repository.save(entity);

        DescripcionesDTO response = new DescripcionesDTO();
        response.setId(saved.getId());
        response.setDescripcion(saved.getDescripcion());
        response.setEstado(saved.getEsActivo());
        response.setCantidadMaximaCaracteres(saved.getCantidadMaximaCaracteres());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DescripcionesDTO actualizar(Integer id, DescripcionesDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setDescripcion(dto.getDescripcion());
                    entity.setEsActivo(dto.getEstado());
                    entity.setCantidadMaximaCaracteres(dto.getCantidadMaximaCaracteres());
                    Descripciones updated = repository.save(entity);

                    DescripcionesDTO response = new DescripcionesDTO();
                    response.setId(updated.getId());
                    response.setDescripcion(updated.getDescripcion());
                    response.setEstado(updated.getEsActivo());
                    response.setCantidadMaximaCaracteres(updated.getCantidadMaximaCaracteres());
                    return response;
                })
                .orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
