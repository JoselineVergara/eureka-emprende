package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventosRepository extends JpaRepository<Eventos, Integer> {

    List<Eventos> findByEmprendimientoId(Integer idEmprendimiento);

    List<Eventos> findByEmprendimiento_Usuarios_Id(Integer emprendimientoUsuariosId);

    List<Eventos> findByEstadoEvento(String estadoEvento);}