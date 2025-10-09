package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.EmprendimientoCategorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientoCategoriasRepository extends JpaRepository<EmprendimientoCategorias, Integer> {
}
