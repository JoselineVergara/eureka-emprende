package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.EmprendimientoDTO;
import com.example.eureka.entrepreneurship.dto.EmprendimientoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EmprendimientoResponseDTO;
import com.example.eureka.model.Emprendimientos;
import com.example.eureka.model.Usuarios;
import jakarta.validation.Valid;

import java.util.List;

public interface IEmprendimientoService {

    Integer estructuraEmprendimiento(EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception;

    List<EmprendimientoResponseDTO> obtenerEmprendimientos();

    EmprendimientoResponseDTO obtenerEmprendimientoPorId(Integer id);

    Emprendimientos crearBorradorEmprendimiento(@Valid EmprendimientoDTO emprendimientoDTO, Usuarios usuario);

    EmprendimientoResponseDTO obtenerEmprendimientoCompletoPorId(Integer id);

    EmprendimientoResponseDTO actualizarEmprendimiento(Integer id, EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception;
}
