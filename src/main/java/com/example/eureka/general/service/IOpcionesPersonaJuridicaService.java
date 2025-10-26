package com.example.eureka.general.service;

import com.example.eureka.general.dto.OpcionesPersonaJuridicaDTO;

import java.util.List;

public interface IOpcionesPersonaJuridicaService {

    List<OpcionesPersonaJuridicaDTO> listarOpcionesPersonaJuridica();
    OpcionesPersonaJuridicaDTO obtenerOpcionPersonaJuridicaPorId(Integer id);
    OpcionesPersonaJuridicaDTO crearOpcionPersonaJuridica(OpcionesPersonaJuridicaDTO dto);
    OpcionesPersonaJuridicaDTO actualizarOpcionPersonaJuridica(Integer id, OpcionesPersonaJuridicaDTO dto);
    void eliminarOpcionPersonaJuridica(Integer id);

}
