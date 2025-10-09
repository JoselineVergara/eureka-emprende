package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;

import java.util.List;

public interface IEventosService {

    EventoResponseDTO crearEvento(EventoRequestDTO request, Integer idUsuario);

    EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO request, Integer idUsuario);

    void inactivarEvento(Integer idEvento, Integer idUsuario);

    List<EventoResponseDTO> obtenerTodosLosEventos();

    List<EventoResponseDTO> obtenerEventosPorEmprendimiento(Integer idEmprendimiento);

    List<EventoResponseDTO> obtenerEventosPorUsuario(Integer idUsuario);

    EventoResponseDTO obtenerEventoPorId(Integer idEvento);
}