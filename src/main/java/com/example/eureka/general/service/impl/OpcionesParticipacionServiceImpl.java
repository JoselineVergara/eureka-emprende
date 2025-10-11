package com.example.eureka.general.service.impl;

import com.example.eureka.general.dto.OpcionesParticipacionComunidadDTO;
import com.example.eureka.general.repository.IOpcionesParticipacionComunidadRepository;
import com.example.eureka.general.service.IOpcionesParticipacionComunidadService;
import com.example.eureka.model.OpcionesParticipacionComunidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpcionesParticipacionServiceImpl implements IOpcionesParticipacionComunidadService {

    private final IOpcionesParticipacionComunidadRepository repository;

    @Override
    public List<OpcionesParticipacionComunidadDTO> listar() {
        return repository.findAll()
                .stream()
                .map(entity -> {
                    OpcionesParticipacionComunidadDTO dto = new OpcionesParticipacionComunidadDTO();
                    dto.setId(entity.getId());
                    dto.setOpcion(entity.getOpcion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OpcionesParticipacionComunidadDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(entity -> {
                    OpcionesParticipacionComunidadDTO dto = new OpcionesParticipacionComunidadDTO();
                    dto.setId(entity.getId());
                    dto.setOpcion(entity.getOpcion());
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public OpcionesParticipacionComunidadDTO guardar(OpcionesParticipacionComunidadDTO dto) {
        OpcionesParticipacionComunidad entity = new OpcionesParticipacionComunidad();
        entity.setOpcion(dto.getOpcion());

        OpcionesParticipacionComunidad saved = repository.save(entity);

        OpcionesParticipacionComunidadDTO response = new OpcionesParticipacionComunidadDTO();
        response.setId(saved.getId());
        response.setOpcion(saved.getOpcion());
        return response;
    }

    @Override
    public OpcionesParticipacionComunidadDTO actualizar(Integer id, OpcionesParticipacionComunidadDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setOpcion(dto.getOpcion());
                    OpcionesParticipacionComunidad updated = repository.save(entity);

                    OpcionesParticipacionComunidadDTO response = new OpcionesParticipacionComunidadDTO();
                    response.setId(updated.getId());
                    response.setOpcion(updated.getOpcion());
                    return response;
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
