package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.TiposPresenciaDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientoPresenciaDigitalRepository extends JpaRepository<TiposPresenciaDigital, Integer> {
}
