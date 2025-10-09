package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.TiposDescripcionEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmprendimientosDescripcionRepository extends JpaRepository<TiposDescripcionEmprendimiento, Integer> {


}
