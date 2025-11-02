package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.TiposEmprendimientos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITiposEmprendimientoRepository extends JpaRepository<TiposEmprendimientos,Integer> {
}
