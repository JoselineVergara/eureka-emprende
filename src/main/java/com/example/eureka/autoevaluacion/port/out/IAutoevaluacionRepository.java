package com.example.eureka.autoevaluacion.port.out;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutoevaluacionRepository extends JpaRepository<Respuesta, Integer> {

    Page<Respuesta> findAllByEsAutoEvaluacionTrue(Pageable pageable);

}
