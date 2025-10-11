package com.example.eureka.general.service;

import com.example.eureka.general.dto.CiudadDTO;

import java.util.List;

public interface ICiudadService {
    List<CiudadDTO> listar();
    CiudadDTO obtenerPorId(Integer id);
    CiudadDTO guardar(CiudadDTO dto);
    CiudadDTO actualizar(Integer id, CiudadDTO dto);
    void eliminar(Integer id);
}