package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Emprendimientos;
import com.example.eureka.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientosRepository extends JpaRepository<Emprendimientos, Integer> {
    List<Emprendimientos> findByUsuarios(Usuarios usuarios);
}
