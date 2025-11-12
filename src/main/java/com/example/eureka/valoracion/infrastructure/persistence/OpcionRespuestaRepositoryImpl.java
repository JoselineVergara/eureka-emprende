package com.example.eureka.valoracion.infrastructure.persistence;

import com.example.eureka.valoracion.domain.model.OpcionRespuesta;
import com.example.eureka.valoracion.port.out.IOpcionRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpcionRespuestaRepositoryImpl implements IOpcionRespuestaRepository {

    private final OpcionRespuestaJpaRepository jpaRepository;

    @Override
    public OpcionRespuesta save(OpcionRespuesta opcionRespuesta) {
        return jpaRepository.save(opcionRespuesta);
    }

    @Override
    public List<OpcionRespuesta> saveAll(List<OpcionRespuesta> opcionesRespuesta) {
        return jpaRepository.saveAll(opcionesRespuesta);
    }

    @Override
    public List<OpcionRespuesta> findByRespuestaId(Long idRespuesta) {
        return jpaRepository.findByIdRespuestaWithDetails(idRespuesta);
    }
}