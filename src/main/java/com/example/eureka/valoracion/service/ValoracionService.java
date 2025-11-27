package com.example.eureka.valoracion.service;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.Respuesta;
import com.example.eureka.valoracion.dto.EmprendimientoInfo;
import com.example.eureka.valoracion.dto.RespuestaFormularioDTO;

import java.util.List;

public interface ValoracionService {

    List<Respuesta> findAllByEmprendimientos(Emprendimientos emprendimientos);

    List<EmprendimientoInfo> obtenerEmprendimientos();

    boolean existsByEmprendimientos(Emprendimientos emprendimientos);

    List<RespuestaFormularioDTO> obtenerRespuestasPorEmprendimiento(Long idEmprendimiento);
}
