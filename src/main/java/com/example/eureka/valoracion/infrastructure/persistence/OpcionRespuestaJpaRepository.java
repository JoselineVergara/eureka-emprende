package com.example.eureka.valoracion.infrastructure.persistence;

import com.example.eureka.valoracion.domain.model.OpcionRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface OpcionRespuestaJpaRepository extends JpaRepository<OpcionRespuesta, Long> {

    @Query("SELECT or FROM OpcionRespuesta or " +
            "LEFT JOIN FETCH or.opcion o " +
            "LEFT JOIN FETCH o.pregunta " +
            "WHERE or.idRespuesta = :idRespuesta")
    List<OpcionRespuesta> findByIdRespuestaWithDetails(@Param("idRespuesta") Long idRespuesta);
}