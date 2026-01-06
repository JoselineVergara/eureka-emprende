package com.example.eureka.solicitudes.port.in;

import com.example.eureka.solicitudes.infrastructure.dto.SolicitudEmprendimientoDTO;
import java.util.List;

public interface SolicitudEmprendimientoService {
    SolicitudEmprendimientoDTO crear(SolicitudEmprendimientoDTO dto);
    SolicitudEmprendimientoDTO actualizar(Integer id, SolicitudEmprendimientoDTO dto);
    void eliminar(Integer id);
    SolicitudEmprendimientoDTO obtenerPorId(Integer id);
    List<SolicitudEmprendimientoDTO> obtenerTodos();
}

