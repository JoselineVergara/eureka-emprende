package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.EmprendimientoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EmprendimientoResponseDTO;

import java.util.List;

public interface IEmprendimientoService {

    void estructuraEmprendimiento(EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception;

    List<EmprendimientoResponseDTO> obtenerEmprendimientos();

    EmprendimientoResponseDTO obtenerEmprendimientoPorId(Integer id);
}
