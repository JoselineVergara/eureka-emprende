package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.SolicitudAprobacion;
import com.example.eureka.domain.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISolicitudAprobacionRepository extends JpaRepository<SolicitudAprobacion, Integer> {

    List<SolicitudAprobacion> findByEstadoSolicitudOrderByFechaSolicitudAsc(
            SolicitudAprobacion.EstadoSolicitud estado
    );

    List<SolicitudAprobacion> findByEmprendimientoOrderByFechaSolicitudDesc(
            Emprendimientos emprendimiento
    );

    @Query("SELECT s FROM SolicitudAprobacion s WHERE s.emprendimiento.id = :emprendimientoId " +
            "AND s.estadoSolicitud IN ('PENDIENTE', 'EN_REVISION') " +
            "ORDER BY s.fechaSolicitud DESC")
    Optional<SolicitudAprobacion> findSolicitudActivaByEmprendimientoId(
            @Param("emprendimientoId") Integer emprendimientoId
    );

    List<SolicitudAprobacion> findByUsuarioSolicitanteOrderByFechaSolicitudDesc(
            Usuarios usuario
    );

    Integer countByEstadoSolicitud(SolicitudAprobacion.EstadoSolicitud estado);

    @Query("SELECT s FROM SolicitudAprobacion s " +
            "WHERE s.estadoSolicitud = :estado " +
            "AND s.tipoSolicitud = :tipo " +
            "ORDER BY s.fechaSolicitud ASC")
    List<SolicitudAprobacion> findByEstadoAndTipo(
            @Param("estado") SolicitudAprobacion.EstadoSolicitud estado,
            @Param("tipo") SolicitudAprobacion.TipoSolicitud tipo
    );
}