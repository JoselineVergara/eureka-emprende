package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.model.Eventos;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventosService {

    EventoResponseDTO crearEvento(EventoRequestDTO request, Integer idEmprendimiento);

    EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO request, Integer idEmprendimiento);

    void inactivarEvento(Integer idEvento);

    List<EventoResponseDTO> obtenerEventosPorEmprendimiento(Integer idEmprendimiento);

    List<EventoResponseDTO> obtenerEventosPorUsuario(Integer idUsuario);

    EventoResponseDTO obtenerEventoPorId(Integer idEvento);

    List<EventoResponseDTO> obtenerEventosFiltrados(
            EstadoEvento estadoEvento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin);
}