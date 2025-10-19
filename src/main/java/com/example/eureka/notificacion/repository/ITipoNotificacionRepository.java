package com.example.eureka.notificacion.repository;

import com.example.eureka.model.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITipoNotificacionRepository extends JpaRepository<TipoNotificacion, Long> {

    Optional<TipoNotificacion> findByCodigo(String codigo);

    Optional<TipoNotificacion> findByCodigoAndActivoTrue(String codigo);
}