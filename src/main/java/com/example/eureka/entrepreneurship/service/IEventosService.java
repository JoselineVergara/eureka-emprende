package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.enums.TipoEvento;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventosService {

    /**
     * Crea un nuevo evento asociado a un emprendimiento
     */
    EventoResponseDTO crearEvento(EventoRequestDTO request, Integer idEmprendimiento);

    /**
     * Edita un evento existente
     */
    EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO request, Integer idEmprendimiento);

    /**
     * Cancela un evento (cambio de estado a cancelado)
     */
    void inactivarEvento(Integer idEvento);

    /**
     * Activa un evento (solo admin)
     */
    void activarEvento(Integer idEvento);

    /**
     * Desactiva un evento (solo admin)
     */
    void desactivarEvento(Integer idEvento);

    /**
     * Obtiene todos los eventos de un emprendimiento específico
     */
    List<EventoResponseDTO> obtenerEventosPorEmprendimiento(Integer idEmprendimiento);

    /**
     * Obtiene eventos de un usuario con filtros opcionales
     * @param idUsuario ID del usuario
     * @param tipoEvento (Opcional) Filtrar por tipo de evento
     * @param idEmprendimiento (Opcional) Filtrar por emprendimiento específico
     */
    List<EventoResponseDTO> obtenerEventosPorUsuario(Integer idUsuario, TipoEvento tipoEvento, Integer idEmprendimiento);

    /**
     * Obtiene un evento por su ID
     */
    EventoResponseDTO obtenerEventoPorId(Integer idEvento);

    /**
     * Obtiene eventos filtrados por múltiples criterios
     * @param estadoEvento (Opcional) Estado del evento
     * @param tipoEvento (Opcional) Tipo de evento
     * @param idEmprendimiento (Opcional) ID del emprendimiento
     * @param fechaInicio (Opcional) Fecha de inicio del rango - requerida junto con fechaFin
     * @param fechaFin (Opcional) Fecha de fin del rango - requerida junto con fechaInicio
     */
    List<EventoResponseDTO> obtenerEventosFiltrados(
            EstadoEvento estadoEvento,
            TipoEvento tipoEvento,
            Integer idEmprendimiento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin);
}