package com.example.eureka.entrepreneurship.port.out;

import com.example.eureka.formulario.domain.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPreguntaRepository extends JpaRepository<Pregunta, Long> {
}
