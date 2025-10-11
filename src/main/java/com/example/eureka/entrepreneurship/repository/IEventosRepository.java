package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEventosRepository extends JpaRepository<Eventos, Integer> {

    List<Eventos> findByEmprendimientoId(Integer idEmprendimiento);

    List<Eventos> findByEmprendimiento_Usuarios_Id(Integer emprendimientoUsuariosId);

    List<Eventos> findByEstadoEvento(EstadoEvento estadoEvento);


    @Query("SELECT e FROM Eventos e WHERE e.fechaEvento BETWEEN :fechaInicio AND :fechaFin ORDER BY e.fechaEvento ASC")
    List<Eventos> findByFechaEventoBetween(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar por estado y rango de fechas
    @Query("SELECT e FROM Eventos e WHERE e.estadoEvento = :estadoEvento " +
            "AND e.fechaEvento BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY e.fechaEvento ASC")

    List<Eventos> findByEstadoEventoAndFechaEventoBetween(
            @Param("estadoEvento") EstadoEvento estadoEvento,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    List<Eventos> findByEstadoEventoAndFechaEventoBefore(
            EstadoEvento estado,
            LocalDateTime fechaLimite
    );

}