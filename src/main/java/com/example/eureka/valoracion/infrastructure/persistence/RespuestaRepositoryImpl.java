package com.example.eureka.valoracion.infrastructure.persistence;

import com.example.eureka.valoracion.domain.model.Respuesta;
import com.example.eureka.valoracion.port.out.IRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RespuestaRepositoryImpl implements IRespuestaRepository {

    private final RespuestaJpaRepository jpaRepository;

    @Override
    public Respuesta save(Respuesta respuesta) {
        return jpaRepository.save(respuesta);
    }

    @Override
    public Optional<Respuesta> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Respuesta> findByEmprendimientoId(Integer idEmprendimiento) {
        return jpaRepository.findByIdEmprendimiento(idEmprendimiento);
    }

    @Override
    public List<Respuesta> findByEmprendimientoIdAndFormularioId(Integer idEmprendimiento, Long idFormulario) {
        return jpaRepository.findByIdEmprendimientoAndIdFormulario(idEmprendimiento, idFormulario);
    }

    @Override
    public List<Respuesta> findByFormularioId(Long idFormulario) {
        return jpaRepository.findByIdFormulario(idFormulario);
    }

    @Override
    public List<Respuesta> findByEmprendimientoIdAndFechaBetween(
            Integer idEmprendimiento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        return jpaRepository.findByIdEmprendimientoAndFechaRespuestaBetween(
                idEmprendimiento, fechaInicio, fechaFin
        );
    }

    @Override
    public Double calcularPromedioValoracion(Integer idEmprendimiento, Long idFormulario) {
        return jpaRepository.calcularPromedioValoracion(idEmprendimiento, idFormulario);
    }

    @Override
    public boolean existeAutoevaluacionParaValoracion(Long idValoracion) {
        return jpaRepository.existsByIdValoracionOrigen(idValoracion);
    }

    @Override
    public Page<Respuesta> findAutoevaluacionesByEmprendimiento(Integer idEmprendimiento, Pageable pageable) {
        return jpaRepository.findAutoevaluacionesByEmprendimiento(idEmprendimiento, pageable);
    }

    @Override
    public Page<Respuesta> findAllAutoevaluaciones(Pageable pageable) {
        return jpaRepository.findAllAutoevaluaciones(pageable);
    }

    @Override
    public Double calcularPromedioDeValoracion(Long idValoracion) {
        return jpaRepository.calcularPromedioDeValoracion(idValoracion);
    }
}