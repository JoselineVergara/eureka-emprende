package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Emprendimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientosRepository extends JpaRepository<Emprendimientos, Integer> {

}
