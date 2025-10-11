package com.example.eureka.general.repository;

import com.example.eureka.model.TiposEmprendimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoEmprendimientoRepository extends JpaRepository<TiposEmprendimientos,Integer> {
}
