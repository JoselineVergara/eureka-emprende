package com.example.eureka.general.repository;

import com.example.eureka.domain.model.OpcionesParticipacionComunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOpcionesParticipacionComunidadRepository extends JpaRepository<OpcionesParticipacionComunidad,Integer> {
}
