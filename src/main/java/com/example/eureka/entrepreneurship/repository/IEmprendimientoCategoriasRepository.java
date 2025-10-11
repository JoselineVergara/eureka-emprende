package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.EmprendimientoCategorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientoCategoriasRepository extends JpaRepository<EmprendimientoCategorias, Integer> {
    List<EmprendimientoCategorias> findByEmprendimientoId(Integer emprendimientoId);

    void deleteEmprendimientoCategoriasByEmprendimientoId(Integer emprendimientoId);
}
