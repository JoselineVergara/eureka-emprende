package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.request.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.shared.EventoResponseDTO;
import com.example.eureka.entrepreneurship.dto.admin.EventoAdminDTO;
import com.example.eureka.entrepreneurship.dto.shared.EventoEmprendedorDTO;
import com.example.eureka.entrepreneurship.dto.publico.EventoPublicoDTO;
import com.example.eureka.domain.enums.EstadoEvento;
import com.example.eureka.domain.enums.TipoEvento;
import com.example.eureka.shared.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EventosService {

    /**
     * Crea un nuevo evento asociado a un emprendimiento
     * @param request Datos del evento
     * @param idEmprendimiento ID del emprendimiento
     * @param idUsuario ID del usuario que crea (validación de permisos)
     */
    EventoResponseDTO crearEvento(EventoRequestDTO request, Integer idEmprendimiento, Integer idUsuario);

    /**
     * Edita un evento existente
     * @param idEvento ID del evento a editar
     * @param request Nuevos datos del evento
     * @param idEmprendimiento ID del emprendimiento (validación)
     * @param idUsuario ID del usuario que edita (validación de permisos)
     */
    EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO request, Integer idEmprendimiento, Integer idUsuario);

    /**
     * Cancela un evento (cambio de estado a cancelado)
     * @param idEvento ID del evento
     * @param idUsuario ID del usuario (validación de permisos)
     */
    void cancelarEvento(Integer idEvento, Integer idUsuario);

    /**
     * Activa un evento (solo admin)
     * @param idEvento ID del evento
     */
    void activarEvento(Integer idEvento);

    /**
     * Desactiva un evento (solo admin)
     * @param idEvento ID del evento
     */
    void desactivarEvento(Integer idEvento);

    /**
     * Obtiene eventos públicos (solo programados)
     * @param titulo Filtro por nombre (opcional)
     * @param mes Filtro por mes (opcional, 1-12)
     * @param tipoEvento Filtro por tipo de evento (opcional)
     * @param pageable Configuración de paginación
     * @return Página de eventos públicos
     */
    PageResponseDTO<EventoPublicoDTO> obtenerEventosPublicos(
            String titulo,
            Integer mes,
            TipoEvento tipoEvento,
            Pageable pageable);

    /**
     * Obtiene un evento público por ID (solo si está programado)
     * @param idEvento ID del evento
     * @return Evento completo
     */
    EventoResponseDTO obtenerEventoPublicoPorId(Integer idEvento);

    /**
     * Obtiene eventos del emprendedor autenticado
     * @param idUsuario ID del usuario emprendedor
     * @param titulo Filtro por nombre (opcional)
     * @param fechaInicio Filtro por fecha inicio (opcional)
     * @param fechaFin Filtro por fecha fin (opcional)
     * @param estado Filtro por estado (opcional)
     * @param tipoEvento Filtro por tipo (opcional)
     * @param pageable Configuración de paginación
     * @return Página de eventos del emprendedor
     */
    PageResponseDTO<EventoEmprendedorDTO> obtenerEventosEmprendedor(
            Integer idUsuario,
            String titulo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            EstadoEvento estado,
            TipoEvento tipoEvento,
            Pageable pageable);

    /**
     * Obtiene todos los eventos (admin)
     * @param titulo Filtro por nombre (opcional)
     * @param fechaInicio Filtro por fecha inicio (opcional)
     * @param fechaFin Filtro por fecha fin (opcional)
     * @param estado Filtro por estado (opcional)
     * @param tipoEvento Filtro por tipo (opcional)
     * @param pageable Configuración de paginación
     * @return Página de eventos (vista admin)
     */
    PageResponseDTO<EventoAdminDTO> obtenerEventosAdmin(
            String titulo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            EstadoEvento estado,
            TipoEvento tipoEvento,
            Pageable pageable);

    /**
     * Obtiene un evento por ID (admin - cualquier estado)
     * @param idEvento ID del evento
     * @return Evento completo
     */
    EventoResponseDTO obtenerEventoPorId(Integer idEvento);
}