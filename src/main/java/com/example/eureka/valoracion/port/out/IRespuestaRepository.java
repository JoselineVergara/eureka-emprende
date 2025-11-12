package com.example.eureka.valoracion.port.out;

import com.example.eureka.valoracion.domain.model.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IRespuestaRepository {

    Respuesta save(Respuesta respuesta);

    Optional<Respuesta> findById(Long id);

    List<Respuesta> findByEmprendimientoId(Integer idEmprendimiento);

    List<Respuesta> findByFormularioId(Long idFormulario);

    List<Respuesta> findByEmprendimientoIdAndFormularioId(Integer idEmprendimiento, Long idFormulario);

    List<Respuesta> findByEmprendimientoIdAndFechaBetween(
            Integer idEmprendimiento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    Double calcularPromedioValoracion(Integer idEmprendimiento, Long idFormulario);

    boolean existeAutoevaluacionParaValoracion(Long idValoracion);

    Page<Respuesta> findAutoevaluacionesByEmprendimiento(Integer idEmprendimiento, Pageable pageable);

    Page<Respuesta> findAllAutoevaluaciones(Pageable pageable);

    Double calcularPromedioDeValoracion(Long idValoracion);
}