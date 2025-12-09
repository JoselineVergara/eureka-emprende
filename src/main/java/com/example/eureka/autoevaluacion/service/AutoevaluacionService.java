package com.example.eureka.autoevaluacion.service;

import com.example.eureka.autoevaluacion.dto.RespuestaResponseDTO;
import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.Respuesta;
import com.example.eureka.autoevaluacion.dto.EmprendimientoInfo;
import com.example.eureka.autoevaluacion.dto.RespuestaFormularioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AutoevaluacionService {

    List<Respuesta> findAllByEmprendimientos(Emprendimientos emprendimientos);

    Respuesta findById(Long idRespuesta);

    Page<EmprendimientoInfo> obtenerEmprendimientos(Pageable pageable);

    boolean existsByEmprendimientos(Emprendimientos emprendimientos);

    List<RespuestaFormularioDTO> obtenerRespuestasPorEmprendimiento(Long idEmprendimiento);

    Respuesta saveRespuesta(RespuestaResponseDTO respuesta);

}
