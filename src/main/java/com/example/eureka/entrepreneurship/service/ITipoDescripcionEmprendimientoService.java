package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.TipoDescripcionEmprendimientoDTO;

import java.util.List;

public interface ITipoDescripcionEmprendimientoService {
    List<TipoDescripcionEmprendimientoDTO> listar();
    TipoDescripcionEmprendimientoDTO obtenerPorId(Integer id);
    TipoDescripcionEmprendimientoDTO guardar(TipoDescripcionEmprendimientoDTO dto);
    TipoDescripcionEmprendimientoDTO actualizar(Integer id, TipoDescripcionEmprendimientoDTO dto);
    void eliminar(Integer id);
}
