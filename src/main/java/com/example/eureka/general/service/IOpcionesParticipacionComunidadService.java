package com.example.eureka.general.service;

import com.example.eureka.general.dto.OpcionesParticipacionComunidadDTO;

import java.util.List;

public interface IOpcionesParticipacionComunidadService {
    List<OpcionesParticipacionComunidadDTO> listar();
    OpcionesParticipacionComunidadDTO obtenerPorId(Integer id);
    OpcionesParticipacionComunidadDTO guardar(OpcionesParticipacionComunidadDTO dto);
    OpcionesParticipacionComunidadDTO actualizar(Integer id, OpcionesParticipacionComunidadDTO dto);
    void eliminar(Integer id);
}
