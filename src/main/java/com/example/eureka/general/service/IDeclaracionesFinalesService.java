package com.example.eureka.general.service;

import com.example.eureka.general.dto.DeclaracionesFinalesDTO;
import java.util.List;

public interface IDeclaracionesFinalesService {
    List<DeclaracionesFinalesDTO> listar();
    DeclaracionesFinalesDTO obtenerPorId(Integer id);
    DeclaracionesFinalesDTO guardar(DeclaracionesFinalesDTO dto);
    DeclaracionesFinalesDTO actualizar(Integer id, DeclaracionesFinalesDTO dto);
    void eliminar(Integer id);
}
