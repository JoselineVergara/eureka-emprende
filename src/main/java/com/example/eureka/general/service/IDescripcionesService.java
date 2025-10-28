package com.example.eureka.general.service;

import com.example.eureka.general.dto.DescripcionesDTO;

import java.util.List;

public interface IDescripcionesService {
    List<DescripcionesDTO> listar();
    DescripcionesDTO obtenerPorId(Integer id);
    DescripcionesDTO guardar(DescripcionesDTO dto);
    DescripcionesDTO actualizar(Integer id, DescripcionesDTO dto);
    void eliminar(Integer id);
}
