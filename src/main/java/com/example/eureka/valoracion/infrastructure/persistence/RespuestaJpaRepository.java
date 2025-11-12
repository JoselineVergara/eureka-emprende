package com.example.eureka.valoracion.infrastructure.persistence;

import com.example.eureka.valoracion.domain.model.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface RespuestaJpaRepository extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findByIdEmprendimiento(Integer idEmprendimiento);

    List<Respuesta> findByIdEmprendimientoAndIdFormulario(Integer idEmprendimiento, Long idFormulario);

    List<Respuesta> findByIdFormulario(Long idFormulario);

    List<Respuesta> findByIdEmprendimientoAndFechaRespuestaBetween(
            Integer idEmprendimiento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    @Query("SELECT AVG(or.valorEscala) FROM OpcionRespuesta or " +
            "WHERE or.respuesta.idEmprendimiento = :idEmprendimiento " +
            "AND or.respuesta.formulario.tipoFormulario.nombre IN ('EVALUACION_SERVICIO', 'EVALUACION_PRODUCTO') " +
            "AND or.valorEscala IS NOT NULL")
    Double calcularPromedioValoracion(@Param("idEmprendimiento") Integer idEmprendimiento);

    boolean existsByIdValoracionOrigen(Long idValoracionOrigen);

    @Query("SELECT r FROM Respuesta r WHERE r.esAutoevaluacion = true AND r.idEmprendimiento = :idEmprendimiento")
    Page<Respuesta> findAutoevaluacionesByEmprendimiento(@Param("idEmprendimiento") Integer idEmprendimiento, Pageable pageable);

    @Query("SELECT r FROM Respuesta r WHERE r.esAutoevaluacion = true")
    Page<Respuesta> findAllAutoevaluaciones(Pageable pageable);

    @Query("SELECT AVG(or.valorEscala) FROM OpcionRespuesta or WHERE or.idRespuesta = :idRespuesta AND or.valorEscala IS NOT NULL")
    Double calcularPromedioDeValoracion(@Param("idRespuesta") Long idRespuesta);
}
