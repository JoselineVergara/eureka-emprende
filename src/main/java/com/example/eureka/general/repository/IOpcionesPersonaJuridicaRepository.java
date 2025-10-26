package com.example.eureka.general.repository;

import com.example.eureka.model.OpcionesPersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOpcionesPersonaJuridicaRepository extends JpaRepository<OpcionesPersonaJuridica,Integer> {

    @Query("SELECT o FROM OpcionesPersonaJuridica as o  where o.estado = TRUE")
    List<OpcionesPersonaJuridica> findAll();

    @Query("SELECT o FROM OpcionesPersonaJuridica as o where o.id=:id and o.estado = TRUE")
    OpcionesPersonaJuridica findById(int id);

}
