package com.example.eureka.solicitudes.port.out;

import com.example.eureka.solicitudes.domain.model.SolicitudAprobacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISolicitudAprobacionRepository extends JpaRepository<SolicitudAprobacion, Integer> {

    Page<SolicitudAprobacion> findByEstadoSolicitudOrderByFechaSolicitudAsc(
            SolicitudAprobacion.EstadoSolicitud estado,
            Pageable pageable
    );

    @Query("SELECT s FROM SolicitudAprobacion s WHERE s.emprendimiento.id = :emprendimientoId " +
            "AND s.estadoSolicitud IN ('PENDIENTE', 'EN_REVISION') " +
            "ORDER BY s.fechaSolicitud DESC")
    Optional<SolicitudAprobacion> findSolicitudActivaByEmprendimientoId(
            @Param("emprendimientoId") Integer emprendimientoId
    );

}