package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.SolicitudEmprendimientoDTO;
import java.util.List;

public interface ISolicitudEmprendimientoService {
    SolicitudEmprendimientoDTO crear(SolicitudEmprendimientoDTO dto);
    SolicitudEmprendimientoDTO actualizar(Integer id, SolicitudEmprendimientoDTO dto);
    void eliminar(Integer id);
    SolicitudEmprendimientoDTO obtenerPorId(Integer id);
    List<SolicitudEmprendimientoDTO> obtenerTodos();
}

