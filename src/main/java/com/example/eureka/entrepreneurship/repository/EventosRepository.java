package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {

    List<Eventos> findByEmprendimientoId(Integer idEmprendimiento);

    List<Eventos> findByEmprendimientoUsuarioIdUsuario(Integer idUsuario);

    List<Eventos> findByEstadoEvento(String estadoEvento);}