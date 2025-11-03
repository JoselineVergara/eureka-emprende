package com.example.eureka.general.service;

import com.example.eureka.general.dto.TipoEmprendimientoDTO;

import java.util.List;

public interface TiposEmprendimientoService {

    List<TipoEmprendimientoDTO> listar();
    TipoEmprendimientoDTO obtenerPorId(Integer id);
    TipoEmprendimientoDTO guardar(TipoEmprendimientoDTO dto);
    TipoEmprendimientoDTO actualizar(Integer id, TipoEmprendimientoDTO dto);
    void eliminar(Integer id);
}
